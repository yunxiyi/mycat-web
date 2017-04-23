package cn.edu.nwsuaf.service.impl;

import cn.edu.nwsuaf.model.UserConfig;
import cn.edu.nwsuaf.service.RedisService;
import cn.edu.nwsuaf.service.UserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by huangrongchao on 2017/4/21.
 */
@Service
public class UserConfigServiceImpl implements UserConfigService {

    @Autowired
    private RedisService redisService;


    @Override
    public UserConfig set(String key, UserConfig t) {
        return redisService.set(key, t, UserConfig.class);
    }

    @Override
    public UserConfig get(String key) {
        return redisService.get(key, UserConfig.class);
    }

    @Override
    public List<UserConfig> multiGet(String pattern) {
        return redisService.multiGet(pattern, UserConfig.class);
    }

    @Override
    public List<UserConfig> getAllByName(String schema) {
        List<UserConfig> userConfigs = multiGet("user:*");
        List<UserConfig> existsSchema = new ArrayList<>();
        userConfigs.forEach(userConfig -> {
            if (userConfig.getSchemas().contains(schema)) {
                existsSchema.add(userConfig);
            }
        });
        return existsSchema;
    }

    @Override
    public List<UserConfig> getAll(Set<String> schemas) {
        List<UserConfig> userConfigs = new ArrayList<>();
        schemas.forEach(s -> {
            userConfigs.addAll(getAllByName(s));
        });
        return userConfigs;
    }
}
