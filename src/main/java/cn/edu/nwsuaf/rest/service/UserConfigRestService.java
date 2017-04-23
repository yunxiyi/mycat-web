package cn.edu.nwsuaf.rest.service;

import cn.edu.nwsuaf.model.User;
import cn.edu.nwsuaf.model.UserConfig;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by huangrongchao on 2017/4/18.
 */
@Service
public class UserConfigRestService {
    @Autowired
    private RestService restService;

    public List<UserConfig> getAll(String username, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        String url = "http://localhost:8080/api/v1/getUsers";
        String res = restService.post(url, params);

        List<UserConfig> userConfigs = JSON.parseArray(res, UserConfig.class);

        return userConfigs;
    }

    public UserConfig get() {
        List<UserConfig> userConfigs = getAll("admin", "admin");

        for (UserConfig userConfig : userConfigs) {
            if (!userConfig.isUsed()) {
                return userConfig;
            }
        }

        return null;
    }

    public boolean set(UserConfig userConfig) {
        String url = "http://localhost:8080/api/v1/changeUserInfo";
        return restService.get(url, userConfig) == null ? false : true;
    }
}
