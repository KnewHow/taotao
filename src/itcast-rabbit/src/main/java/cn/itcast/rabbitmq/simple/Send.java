package cn.itcast.rabbitmq.simple;

import java.io.IOException;

import cn.itcast.rabbitmq.utils.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    public static final String QUEUE_NAME = "test_queue";

    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中获取通道
        Channel channel = connection.createChannel();
        // 声明（创建）队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 定义消息内容
        String message = "Hello world";

        // 发送消息
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

        System.out.println(" [x] Sent '" + message + "'");

        // 关闭通道和连接
        channel.close();
        connection.close();

    }
}
