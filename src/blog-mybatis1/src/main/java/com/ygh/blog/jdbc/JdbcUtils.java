package com.ygh.blog.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private static Properties properties = null;
    static {
        try {
            InputStream inputStream = JdbcDemo.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties = new Properties();
            properties.load(inputStream);
            Class.forName(properties.getProperty("jdbc.driverClassName"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
    }
}
