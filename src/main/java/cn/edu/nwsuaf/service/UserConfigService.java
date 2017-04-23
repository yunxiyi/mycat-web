package cn.edu.nwsuaf.service;

import cn.edu.nwsuaf.model.UserConfig;

import java.util.List;
import java.util.Set;

/**
 * Created by huangrongchao on 2017/4/21.
 */
public interface UserConfigService {
    UserConfig set(String key, UserConfig t);

    UserConfig get(String key);

    List<UserConfig> multiGet(String pattern);

    List<UserConfig> getAllByName(String schema);

    List<UserConfig> getAll(Set<String> schemas);
}
