package www.student.manage.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class JdbcUtils {

    public static Properties properties=null;
    
    public static InputStream inputStream;
    static{
        inputStream = JdbcTest.class.getClassLoader().getResourceAsStream("dbconfig.properties");
        properties = new Properties();
        try {
            properties.load(inputStream);
            Class.forName(properties.getProperty("driverClassName"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取数据库连接
     * @return
     * @throws SQLException
     * @throws IOException 
     */
    public static Connection getConnection() throws Exception{
        Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
        inputStream.close();
        return connection;
    }
}
