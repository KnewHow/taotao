package cn.itcast.rabbitmq.work;

import cn.itcast.rabbitmq.utils.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Receive1 {
    public static final String QUEUE_NAME = "test_queue_works";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 同一时刻，服务器只会发一条消息给消费者
        channel.basicQos(1);

        QueueingConsumer consumer = new QueueingConsumer(channel);

        /*
         * 监听队列，手动返回完成,flase为手动，true为自动
         * 说明：自动模式下，只要获取了消息，不管客户端出现什么异常，都认为消息被消费
         *      在手动模式下，只有确认返回状态，服务器才会认为此消息被消费，否则该消息将一直处于不可用状态
         */
        channel.basicConsume(QUEUE_NAME, false, consumer);
        while (true) {
            Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            // 休眠
            Thread.sleep(10);
            // 手动返回确认状态
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }

    }
}
