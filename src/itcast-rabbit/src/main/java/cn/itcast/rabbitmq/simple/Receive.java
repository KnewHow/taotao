package cn.itcast.rabbitmq.simple;

import cn.itcast.rabbitmq.utils.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Receive {

    public static final String QUEUE_NAME = "test_queue";

    public static void main(String[] args) throws Exception {
        // 创建连接和通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列，如果该队列已经存在，后台自动忽略此代码
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);

        // 监听队列，手动返回完成
        channel.basicConsume(QUEUE_NAME, true, consumer);

        while (true) {
            // 此方法为阻塞式方法
            Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
        }

    }
}
