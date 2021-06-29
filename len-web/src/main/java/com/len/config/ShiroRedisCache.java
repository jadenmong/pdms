package com.len.config;

import cn.hutool.core.util.ObjectUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

/**
 * 
 */
public class ShiroRedisCache<K,V> implements Cache<K,V> {
    private RedisTemplate redisTemplate;
    /**
     * 前缀，用于标识哪些是Shiro的数据，前缀太长，确实会影响查询效率，可以优化
     */
    private String prefix = "shiro_redis_session";

    public String getPrefix(){
        return prefix + ":";
    }

    public void setPrefix(){
       this.prefix = prefix;
    }

    public ShiroRedisCache(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public ShiroRedisCache(RedisTemplate redisTemplate, String prefix) {
        this(redisTemplate);
        // 此处覆盖了默认的prefix
        this.prefix = prefix;
    }

    @Override
    public V get(K k) throws CacheException {
        if (k == null) {
            return null;
        }
        byte[] bytes = getBytesKey(k);
        return (V) redisTemplate.opsForValue().get(bytes);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        if (k == null || v == null) {
            return null;
        }
        byte[] bytes = getBytesKey(k);
        redisTemplate.opsForValue().set(bytes,v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        if (k == null) {
            return null;
        }
        byte[] bytes = getBytesKey(k);
        V v = (V) redisTemplate.opsForValue().get(bytes);
        redisTemplate.delete(bytes);
        return v;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    @Override
    public int size() {
        return  redisTemplate.getConnectionFactory().getConnection().dbSize().intValue();
    }

    @Override
    public Set<K> keys() {
        byte[] bytes = (getPrefix() + "*").getBytes();
        Set<byte[]> keys = redisTemplate.keys(bytes);
        Set<K> sets = new HashSet<>();
        for (byte[] key : keys) {
            sets.add((K)key);
        }
        return sets;
    }

    @Override
    public Collection<V> values() {
        Set<K> keys = keys();
        List<V> values = new ArrayList<>(keys.size());
        for (K k : keys) {
            values.add(get(k));
        }
        return values;
    }

    private byte[] getBytesKey(K key){
        if (key instanceof String) {
            String prekey = this.getPrefix() + key;
            return prekey.getBytes();
        } else {
            return ObjectUtil.serialize(key);
        }
    }
}