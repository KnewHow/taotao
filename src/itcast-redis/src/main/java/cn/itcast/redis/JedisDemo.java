package cn.itcast.redis;

import redis.clients.jedis.Jedis;

public class JedisDemo {

    public static void main(String[] args) {
        //创建jedis对象
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //向数据库中添加对象
        jedis.set("myTest", "myTest");
        //获取只值
        String value = jedis.get("myTest");
        System.out.println(value);
        //关闭连接
        jedis.close();
    }
}
