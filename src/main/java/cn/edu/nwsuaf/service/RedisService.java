package cn.edu.nwsuaf.service;

import java.util.List;

/**
 * Created by huangrongchao on 2017/4/21.
 */
public interface RedisService {
    <T> T set(String key, T t, Class<T> clazz);

    <T> T get(String key, Class<T> clazz);

    <T> List<T> multiGet(String pattern, Class<T> clazz);
}
