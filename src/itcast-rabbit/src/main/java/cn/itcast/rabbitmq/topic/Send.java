package cn.itcast.rabbitmq.topic;

import cn.itcast.rabbitmq.utils.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {
    public static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明交换机类型为topic
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String message = "id=1001";
        channel.basicPublish(EXCHANGE_NAME, "item.delete", null, message.getBytes());
        System.out.println("[x]send'" + message + "'");

        channel.close();
        connection.close();
    }
}
