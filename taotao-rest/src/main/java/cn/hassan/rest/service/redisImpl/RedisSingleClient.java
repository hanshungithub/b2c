package cn.hassan.rest.service.redisImpl;

import cn.hassan.rest.service.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisSingleClient implements JedisClient {

    @Autowired
    private JedisPool jedisPool;

    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String s = jedis.get(key);
        jedis.close();
        return s;
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String set = jedis.set(key, value);
        jedis.close();
        return set;
    }

    @Override
    public String hget(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        String hget = jedis.hget(hkey, key);
        jedis.close();
        return hget;
    }

    @Override
    public long hset(String hkey, String key, String value) {
        Jedis jedis = jedisPool.getResource();
        Long hset = jedis.hset(hkey, key, value);
        jedis.close();
        return hset;
    }

    @Override
    public long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long incr = jedis.incr(key);
        jedis.close();
        return incr;
    }

    @Override
    public long expire(String key, int second) {
        Jedis jedis = jedisPool.getResource();
        Long expire = jedis.expire(key, second);
        jedis.close();
        return expire;
    }

    @Override
    public long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long ttl = jedis.ttl(key);
        jedis.close();
        return ttl;
    }

    @Override
    public long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long del = jedis.del(key);
        jedis.close();
        return del;
    }

    @Override
    public long hdel(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        Long hdel = jedis.hdel(hkey, key);
        jedis.close();
        return hdel;
    }
}
