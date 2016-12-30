package cn.itcast.rabbitmq.routing;

import cn.itcast.rabbitmq.utils.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 * 前台系统，需要后台给出update和delete提示
 * 
 * @author Administrator
 *
 */
public class Receive1 {

    public static final String EXCHANGE_NAME = "test_exchange_direct";

    public static final String QUEUE_NAME = "test_queue_direct1";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 绑定队列到交换机，并指定key为update和delete
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "update");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "delete");

        // 同一时刻，服务器只会发一条消息给消费者
        channel.basicQos(1);

        // 创建队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);

        // 指定为手动返回确认状态
        channel.basicConsume(QUEUE_NAME, false, consumer);

        while (true) {
            Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("[x] Received'" + message + "'");
            Thread.sleep(10);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }

    }
}
