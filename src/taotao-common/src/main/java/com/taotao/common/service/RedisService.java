package com.taotao.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {

    @Autowired(required=false)
    private ShardedJedisPool shardedJedisPool;
    
    private<T> T execute(Function<T, ShardedJedis> fun){
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = this.shardedJedisPool.getResource();
            return fun.callback(shardedJedis);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(shardedJedis!=null){
                //如果连接有效，释放连接
                shardedJedis.close();
            }
        }
        return null;
    }
    
    /**
     * 执行 GET
     * @param key
     * @param value
     * @return
     */
    public String set(final String key,final String value){
        return this.execute(new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis e) {
                return e.set(key, value);
            }
        });
    }
    
    
    /**
     * 在存入数据的同时，设置生存周期，单位秒
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public String set(final String key,final String value,final Integer seconds){
        return this.execute(new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis e) {
                String result =  e.set(key, value);
                e.expire(key, seconds);
                return result;
            }
        });
    }
    
    /**
     * 执行SET
     * @param key
     * @return
     */
    public String get(final String key){
        return this.execute(new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis e) {
                return e.get(key) ;
            }
        });
    }
    
    /**
     * 删除 key
     * @param key
     * @return
     */
    public Long del(final String key){
        return this.execute(new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis e) {
                return e.del(key);
            }
        });
    }
    
    /**
     * 给一个key设置生存周期，单位秒
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(final String key,final Integer seconds){
        return this.execute(new Function<Long, ShardedJedis>() {
            @Override
            public Long callback(ShardedJedis e) {
                return e.expire(key, seconds);
            }
        });
    }
    
    
    
}
