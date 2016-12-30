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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manage.bean.PicUploadResult;
import com.taotao.manage.service.PropertiesService;

//@Controller
//@RequestMapping("/pic")
public class PicUploadControllerPractise {

    @Autowired
    private PropertiesService propertiesService;

    // 创建日志
    private static final Logger LOGGER = LoggerFactory.getLogger(PicUploadControllerPractise.class);

    // 创建json格式的对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 校验图片的后缀名
    private static final String[] IMAGE_TYPE = new String[] { ".bmp", ".jpg", ".jpeg", ".gif", ".png" };

    /**
     * 要求返回json格式的文本数据
     * 
     * @param upload
     * @param response
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/upload",method=RequestMethod.POST,produces=MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String upload(@RequestParam(value = "uploadFile") MultipartFile upload, HttpServletResponse response)
            throws JsonProcessingException {

        // 校验图片的后缀名
        String originalFilename = upload.getOriginalFilename();
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWith(originalFilename, type)) {
                isLegal = true;
                break;
            }
        }

        PicUploadResult picUploadResult = new PicUploadResult();
        picUploadResult.setError(isLegal ? 0 : 1);
        if (!isLegal) {
            return MAPPER.writeValueAsString(picUploadResult);
        }

        String filePath = this.getFilePath(originalFilename);
        
        //写日志
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Pic file upload .[{}] to [{}] .", upload.getOriginalFilename(), filePath);
        }

        String picUrl = StringUtils.replace(
                StringUtils.substringAfter(filePath, propertiesService.REPOSITORY_PATH), "\\", "/");

        picUploadResult.setUrl(this.propertiesService.IMAGE_BASE_URL + picUrl);

        File newFile = new File(filePath);

        try {
            upload.transferTo(newFile);
            isLegal = false;

            // 检查文件是否合格(检查文件是否是图片)
            BufferedImage bufferedImage = ImageIO.read(newFile);
            if (bufferedImage != null) {// 如果上传的文件没有问题
                // 返回上传图片的宽度和高度
                picUploadResult.setWidth(bufferedImage.getWidth() + "");
                picUploadResult.setHeight(bufferedImage.getHeight() + "");
                isLegal = true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
      //再次给结果集设置状态
        picUploadResult.setError(isLegal?0:1);
        if(!isLegal){
           newFile.delete(); 
        }

        return MAPPER.writeValueAsString(picUploadResult);
    }

    private String getFilePath(String sourceName) {
        Date nowDate = new Date();
        String baseFolder = this.propertiesService.REPOSITORY_PATH + File.separator + "images";
        String FileFolder = baseFolder + File.separator + new DateTime(nowDate).toString("yyyy")
                + File.separator + new DateTime(nowDate).toString("MM") + File.separator
                + new DateTime(nowDate).toString("dd");
        File file = new File(FileFolder);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        String fileName = new DateTime(nowDate).toString("yyyyMMddhhmmssSSSS")
                + RandomUtils.nextInt(100, 9999) + "." + StringUtils.substringAfterLast(sourceName, ".");
        return FileFolder + File.separator + fileName;
    }

}
