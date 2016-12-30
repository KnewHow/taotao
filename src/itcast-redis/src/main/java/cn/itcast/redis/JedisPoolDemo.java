package cn.itcast.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**
 * 使用redis连接池来获取连接
 * @author Administrator
 *
 */
public class JedisPoolDemo {
    public static void main(String[] args) {
        //构建连接池配置信息
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //设置最大的连接数
        jedisPoolConfig.setMaxTotal(50);
        //创建连接池
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1",6379);
        //获取连接
        Jedis jedis = jedisPool.getResource();
        //选择第0个数据库
        jedis.select(0);
        String value = jedis.get("myTest");
        System.out.println(value);
        //关闭连接池
        jedisPool.close();
        
    }
}
