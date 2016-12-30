package com.taotao.taotaoweb.test;

import java.io.FileReader;
import java.util.Properties;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Content;

public class EasyUITest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Test
    public void test_1() throws Exception{
        
        Properties properties = new Properties();
        //读取本地磁盘中的字符串，在下面给出文件信息
        FileReader fileReader = new FileReader("D:\\project\\taotaoshop\\test\\easyUI.properties");
        
        properties.load(fileReader);
        
        String jsonData = properties.getProperty("data");
        
        System.out.println(jsonData);
        EasyUIResult.formatToList(jsonData, Content.class);
        
        
    }
    
}
