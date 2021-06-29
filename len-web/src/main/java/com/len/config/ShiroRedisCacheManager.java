package com.len.config;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


//Shiro扩展，使用redis管理缓存
public class ShiroRedisCacheManager extends AbstractCacheManager {
    @Autowired
    RedisTemplate redisTemplate;

    //为了个性化配置redis存储是的key，我们选择了加前缀的方式，所以写了一个带名字及redis操作的构造函数的Cache类
    @Override
    protected Cache createCache(String name) throws CacheException {
        return new ShiroRedisCache(redisTemplate, name);
    }
}
