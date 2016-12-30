package cn.itcast.redis;

import java.util.ArrayList;
import java.util.List;




import cn.itcast.utils.RedisUtils;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class ShardedJedisPoolDemo extends RedisUtils {
    
    

    public static void main(String[] args) {
        //定义连接池的配置信息
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(50);
        //定义集群信息
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        shards.add(new JedisShardInfo(host,port));
        
        //定义集群连接池
        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, shards);
        ShardedJedis shardedJedis = null;
        
        try {
            shardedJedis = shardedJedisPool.getResource();
            String value = shardedJedis.get("myTest");
            System.out.println(value);
        } catch (Exception e) {
            // TODO: handle exception
        }finally{
            if(shardedJedis!=null){
                //如果集群连接有效，就放入连接池中
                shardedJedis.close();
            }
            shardedJedisPool.close();
        }
    }
}
