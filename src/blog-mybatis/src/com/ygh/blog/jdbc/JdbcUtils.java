package com.ygh.blog.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.junit.Test;

public class JdbcUtils {

//    private static Properties properties;
//    static{
//        InputStream inputStream 
//    }
    
    @Test
    public void test() throws Exception{
        File file = new File(this.getClass().getResource("").getPath());
//        InputStream inputStream = new FileInputStream(file);
//        Properties properties = new Properties();
//        properties.load(inputStream);
//        String className = (String) properties.get("jdbc.driverClassName");
//        System.out.println(className);
        System.err.println(file);
    }
    
    @Test
    public void fun1(){
        File file = new File(this.getClass().getResource("").getPath());
        URL url = this.getClass().getClassLoader().getResource("jdbc.properties");
        System.out.println(url);
    }
    
    @Test
    public void fun2() throws IOException{
        File file  = new File("");
        String realPath = file.getCanonicalPath();
        System.out.println(realPath);
        File file2 = new File(realPath+"\\resource\\jdbc.properties");
        Properties properties = new Properties();
        properties.load(new FileInputStream(file2));
        System.out.println(properties.get("jdbc.driverClassName"));
    }
    
    @Test
    public void fun3() throws IOException{
        URL url = this.getClass().getClassLoader().getResource("test.txt");
        System.out.println(url);
    }
    
    @Test
    public void fun4() throws IOException{
        File file = new File("clsspath:test.txt");
        System.out.println(file);
    }
    
//    @Test
    
    
}
