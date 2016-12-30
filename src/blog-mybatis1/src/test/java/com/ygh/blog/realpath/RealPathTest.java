package com.ygh.blog.realpath;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 获取java下面的路径的演示
 */
import org.junit.Test;

public class RealPathTest {

    /**
     * 获取当前类所在的工程路径
     */
    @Test
    public void fun1() {
        File file = new File(this.getClass().getResource("/").getPath());
        // D:\project\taotaoshop\src\blog-mybatis1\target\test-classes
        System.out.println(file);
    }

    /**
     * 获取当前类的绝对路径
     */
    @Test
    public void fun2() {
        File file = new File(this.getClass().getResource("").getPath());
        // D:\project\taotaoshop\src\blog-mybatis1\target\test-classes\com\ygh\blog\realpath
        System.out.println(file);
    }

    /**
     * 获取当前类所在的工程路径，两种方法皆可
     * 
     * @throws IOException
     */
    @Test
    public void fun3() throws IOException {
        File file = new File("");
        String path = file.getCanonicalPath();
        // D:\project\taotaoshop\src\blog-mybatis1
        System.out.println(path);
        // D:\project\taotaoshop\src\blog-mybatis1
        System.out.println(System.getProperty("user.dir"));
    }

    /**
     * 获取当前src下面的文件的路径
     */
    @Test
    public void fun4() {
        URL url = this.getClass().getClassLoader().getResource("jdbc.properties");
        System.out.println(url);
    }

    /**
     * 获取其他源码包下面的文件路径
     */
    @Test
    public void fun5() {
        // 使用这种方法可以获取路径
        URL url = this.getClass().getClassLoader().getResource("test2.txt");
        // file:/D:/project/taotaoshop/src/blog-mybatis1/target/classes/test.txt
        System.out.println(url);
    }

    @Test
    public void fun6() throws Exception {
        URL url = this.getClass().getClassLoader().getResource("test2.txt");
        System.out.println(url.getPath());
        Properties properties = new Properties();
        // 使用这种方式可以获取文件对应的输出流
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        properties.load(inputStream);
        File file = new File(url.getPath());
        System.out.println(properties.get("jdbc.driverClassName"));
    }

}
