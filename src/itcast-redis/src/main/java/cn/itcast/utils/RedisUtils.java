package cn.itcast.utils;

import java.io.IOException;
import java.util.Properties;


public class RedisUtils {

    public static String host;
    public static Integer port;
    static{
        Properties properties = new Properties();
        try {
            properties.load(RedisUtils.class.getClassLoader().getResourceAsStream("redis.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        host = (String) properties.get("host");
        port = Integer.parseInt((String) properties.get("port"));
    }
    
}
