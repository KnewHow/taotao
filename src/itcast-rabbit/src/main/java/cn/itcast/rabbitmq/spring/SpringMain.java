package cn.itcast.rabbitmq.spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:spring/rabbitmq-context.xml");
        // RabbitMQ模板
        RabbitTemplate rabbitTemplate = ctx.getBean(RabbitTemplate.class);

        // 发送消息
        rabbitTemplate.convertAndSend("Hello World");
        Thread.sleep(1000);
        // 销毁容器
        ctx.destroy();

    }
}
