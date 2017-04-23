package cn.edu.nwsuaf.rest.service;

import cn.edu.nwsuaf.model.User;
import cn.edu.nwsuaf.model.UserConfig;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by huangrongchao on 2017/4/18.
 */
@Service
public class RestService {
    RestTemplate restTemplate = new RestTemplate();

    public String post(String url, Map<String, Object> params) {
        try {
            HttpEntity<MultiValueMap<String, Object>> request = getHttpEntity(params);
            ResponseEntity<String> res = restTemplate.postForEntity(url, request, String.class);
            return res.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public String get(String url, Map<String, Object> params) {
        try {
            url = filter(url, params.keySet());
            ResponseEntity<String> res = restTemplate.getForEntity(url, String.class, params);
            return res.getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public String get(String url, Object params) {
        Map<String, Object> paramMap = JSON.parseObject(JSON.toJSONString(params), Map.class);
        return get(url, paramMap);
    }


    private HttpEntity<MultiValueMap<String, Object>> getHttpEntity(Map<String, Object> params) {
        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<String, Object>();
        params.entrySet().forEach(entity -> {
            postParameters.add(entity.getKey(), entity.getValue());
        });

        return new HttpEntity<MultiValueMap<String, Object>>(postParameters, headers);
    }

    private String filter(String url, Set<String> paramSet) {
        StringBuffer params = new StringBuffer();
        paramSet.forEach(param -> {
            params.append(param + "={" + param + "}&");
        });
        params.substring(0, params.length() - 1);

        return url + "?" + params;
    }

    public static void main(String args[]) {
        RestService restService = new RestService();
        Map<String, Object> params = new HashMap<>();
        params.put("username", "admin");
        params.put("password", "admin");

        String url = "http://localhost:8080/api/v1/getUsers";
        String res = restService.post(url, params);
        System.out.println(res);
        List<UserConfig> userConfigs = JSON.parseArray(res, UserConfig.class);
        System.out.println(userConfigs);

        // System.out.print(JSON.toJSON(userConfigs));
    }
}
