package cn.edu.nwsuaf;

import cn.edu.nwsuaf.model.UserConfig;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test26SecurityApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void sendSimpleMail() throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("18829352425@163.com");
        message.setTo("18829352425@qq.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");

        mailSender.send(message);
    }

    @Test
    public void redisTest() {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        //List<UserConfig> userConfigList = redisTemplate.opsForValue().multiGet(redisTemplate.keys("user:*"));
        Set<String> keys = redisTemplate.keys("user:*");
        System.out.println(JSON.toJSONString(keys));
        System.out.println(JSON.toJSONString(operations.multiGet(keys)));
    }

}
