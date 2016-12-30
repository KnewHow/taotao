package cn.itcast.rabbitmq.utils;

import java.io.IOException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {

    public static Connection getConnection() throws IOException {
        // 定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置服务器地址
        factory.setHost("127.0.0.1");
        // 设置端口号
        factory.setPort(5672);
        // 设置用户名密码
        factory.setUsername("taotao");
        factory.setPassword("123");
        // 配置vhost
        factory.setVirtualHost("/taotao");
        Connection connection = factory.newConnection();
        return connection;
    }
}
