package cn.itcast.rabbitmq.topic;


import cn.itcast.rabbitmq.utils.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Receive2 {
    public static final String EXCHANGE_NAME = "test_exchange_topic";

    public static final String QUEUE_NAME = "test_queue_topic1";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //使用topic的通配符#
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "item.delete");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "item.update");
        channel.basicQos(1);
        
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, consumer);
        while(true){
            Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("[x]Received'"+message+"'");
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
        
        
    }

}
