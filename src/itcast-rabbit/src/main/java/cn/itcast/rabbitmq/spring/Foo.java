package cn.itcast.rabbitmq.spring;

public class Foo {

    public void listen(String foo){
        System.out.println("消费者："+foo);
    }
}
