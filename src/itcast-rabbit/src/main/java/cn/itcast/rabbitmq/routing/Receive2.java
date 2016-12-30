package cn.itcast.rabbitmq.routing;

import cn.itcast.rabbitmq.utils.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 * 搜索系统
 * 
 * @author Administrator
 *
 */
public class Receive2 {
    public static final String EXCHANGE_NAME = "test_exchange_direct";

    public static final String QUEUE_NAME = "test_queue_direct2";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "insert");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "delete");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "update");

        // 同一时刻，服务器只会发一条消息给消费者
        channel.basicQos(1);
        
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //指定队列为手动
        channel.basicConsume(QUEUE_NAME, false, consumer);
        
        while(true){
            Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("[x]Received'"+message+"'");
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }

    }

}
