package com.taotao.manage.controller;


import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manage.bean.PicUploadResult;
import com.taotao.manage.service.PropertiesService;

@RequestMapping("/pic")
@Controller
public class PicUploadController {
    //启动日志管理器
    private static final Logger LOGGER = LoggerFactory.getLogger(PicUploadController.class);
    
    @Autowired
    private PropertiesService propertiesService;
    
    //jackson框架，轻易的把java对象序列化成json对象
    private static final ObjectMapper mapper = new ObjectMapper();
    
    //运行上传的图片的后缀名
    private static final String[] IMAGE_TYPE = new String[] { ".bmp", ".jpg", ".jpeg", ".gif", ".png" };
    
    /**
     * 要求返回的结果类型是文本格式的json对象，即text/plain
     * 正常的响应类型是:application/json
     * 使用produce的属性来进行设置，还需要使用@ResponseBody注解来变成序列化为json格式
     * 
     * @param uploadFile 上传文件参数
     * @param response
     * @return
     * @throws JsonProcessingException 
     */
    @RequestMapping(value="/upload",method=RequestMethod.POST,produces=MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String upload(@RequestParam("uploadFile")MultipartFile uploadFile,HttpServletResponse response) throws JsonProcessingException{
        
        //校验图片的后缀名
        boolean isLegal = false;
        for(String type:IMAGE_TYPE){
            if(StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)){//如果满足了后缀名为如下，就把isLegal设置为true
                isLegal = true;
                break;
            }
        }
        //创建上传结果集对象
        PicUploadResult picUploadResult = new PicUploadResult();
        
        //给结果集里面的error属性赋值
        picUploadResult.setError(isLegal?0:1);
        
        if(!isLegal){//如果图片格式不正确，直接结束程序
            return mapper.writeValueAsString(picUploadResult);
        }
        
        //计算文件的绝对存储路径
        String filePath = this.getFilePath(uploadFile.getOriginalFilename());
        
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Pic file upload .[{}] to [{}] .", uploadFile.getOriginalFilename(), filePath);
        }
        
        //通过文件的存储路径，计算文件出文的url路径
        String picUlr = StringUtils.replace(StringUtils.substringAfter(filePath, propertiesService.REPOSITORY_PATH), "\\", "/");
        //把请求的url路径设置给上传结果集对象,在前面还需要加上域名
        picUploadResult.setUrl(propertiesService.IMAGE_BASE_URL+picUlr);
        
        File newFile = new File(filePath);
        
        try {
            //把文件写的磁盘上
            uploadFile.transferTo(newFile);
            
            isLegal = false;
            
            //检查文件是否合格(检查文件是否是图片)
            BufferedImage bufferedImage = ImageIO.read(newFile);
            if(bufferedImage!=null){//如果上传的文件没有问题
                //返回上传图片的宽度和高度
                picUploadResult.setWidth(bufferedImage.getWidth()+"");
                picUploadResult.setHeight(bufferedImage.getHeight()+"");
                isLegal = true;
            }
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //再次给结果集设置状态
        picUploadResult.setError(isLegal?0:1);
        
        if(!isLegal){//如果图片是不合法的,删除图片
            newFile.delete();
        }
        
        return mapper.writeValueAsString(picUploadResult);
    }
    
    /**
     * 给一个基本的上传的文件名称，返回一个经过处理的而且不重复的用于存储的文件mingc
     * @param sourceFileName
     * @return
     */
    private String getFilePath(String sourceFileName){
        //基本目录D:\project\taotaoshop\fileUpload\images
        String baseFolder = propertiesService.REPOSITORY_PATH+File.separator+"images";
        Date nowDate = new Date();
        //文件目录，根据年/月/日来创建目录
        String fileFolder = baseFolder+File.separator+new DateTime(nowDate).toString("yyyy")
                +File.separator+new DateTime(nowDate).toString("MM")+File.separator
                +new DateTime(nowDate).toString("dd");
        File file = new File(fileFolder);
        
        if(!file.isDirectory()){//如果目录不存在，创建
            file.mkdirs();
        }
        String fileName = new DateTime(nowDate).toString("yyyyMMddhhmmssSSSS")
                +RandomUtils.nextInt(100, 9999)+"."+StringUtils.substringAfterLast(sourceFileName, ".");
        return fileFolder+File.separator+fileName;
    }
    
//    @Test
//    public void fun1(){
//        String filePath = this.getFilePath("ygh.jpg");
//        String picUlr = StringUtils.replace(StringUtils.substringAfter(filePath, "D:\\project\\taotaoshop\\fileUpload"), "\\", "/");
//        System.out.println(filePath);
//        System.out.println(picUlr);
//    }
    
}
