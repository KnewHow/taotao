package cn.itcast.rabbitmq.routing;

import cn.itcast.rabbitmq.utils.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    public static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明交换机类型为路由模式(direct)
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String message = "id=1001商品更新了";
        channel.basicPublish(EXCHANGE_NAME, "update", null, message.getBytes());

        System.out.println("[x] send'" + message + "'");

        channel.close();
        connection.close();
    }
}
