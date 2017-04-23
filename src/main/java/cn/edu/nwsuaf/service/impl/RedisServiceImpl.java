package cn.edu.nwsuaf.service.impl;

import cn.edu.nwsuaf.service.RedisService;
import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by huangrongchao on 2017/4/21.
 */
@Service
public class RedisServiceImpl implements RedisService {
    Logger logger = Logger.getLogger(RedisServiceImpl.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public <T> T set(String key, T t, Class<T> clazz) {
        logger.info("redis set value by key = " + key);
        String value = JSON.toJSONString(t);
        redisTemplate.opsForValue().set(key, value);
        return t;
    }

    public <T> T get(String key, Class<T> clazz) {
        logger.info("redis get value by key = " + key);
        String value = redisTemplate.opsForValue().get(key);
        try {
            T target = JSON.parseObject(value, clazz);
            return target;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> List<T> multiGet(String pattern, Class<T> clazz) {
        List<T> targets = new ArrayList<>();
        Set<String> keys = redisTemplate.keys(pattern);
        redisTemplate.opsForValue().multiGet(keys).forEach(value -> {
            T t = JSON.parseObject(value, clazz);
            targets.add(t);
        });
        return targets;
    }

}
