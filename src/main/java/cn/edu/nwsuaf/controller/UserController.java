package cn.edu.nwsuaf.controller;

import cn.edu.nwsuaf.model.Msg;
import cn.edu.nwsuaf.model.User;
import cn.edu.nwsuaf.model.UserConfig;
import cn.edu.nwsuaf.model.UserRole;
import cn.edu.nwsuaf.model.UserSchema;
import cn.edu.nwsuaf.model.enumation.RoleType;
import cn.edu.nwsuaf.rest.service.UserConfigRestService;
import cn.edu.nwsuaf.service.RedisService;
import cn.edu.nwsuaf.service.UserConfigService;
import cn.edu.nwsuaf.service.UserRoleService;
import cn.edu.nwsuaf.service.UserSchemaService;
import cn.edu.nwsuaf.service.UserService;
import cn.edu.nwsuaf.util.SecurityUserUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by huangrongchao on 2017/4/2.
 */
@Controller
public class UserController {

    Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserConfigRestService userConfigRestService;
    @Autowired
    private UserSchemaService userSchemaService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserConfigService userConfigService;


    @RequestMapping("/register")
    public String register() {
        return "registration";
    }

    @RequestMapping("/toRegister")
    @ResponseBody
    public Msg toRegister(User user) {
        Msg msg = check(user);

        if (StringUtils.equals(msg.getResult(), "error")) {
            return msg;
        }
        //插入新用户
        userService.insert(user);

        //插入新用户的角色
        UserRole userRole = new UserRole();
        userRole.setRole(RoleType.ROLE_USER.toString());
        userRole.setUserid(user.getId());
        userRoleService.insert(userRole);


        UserConfig userConfig = userConfigRestService.get();
        UserSchema userSchema = new UserSchema();
        //为新用户添加数据库，如果有多余的数据库，则直接添加，否则标记为数据库
        if (userConfig != null) {
            userConfig.setUsed(true);
            userConfig.setPrivilegesConfig(null);
            userConfigRestService.set(userConfig);
            userSchema.setUserid(user.getId());
            userSchema.setHasschema(true);
            userSchema.setSchemaUserName(userConfig.getName());
        } else {
            userSchema.setUserid(user.getId());
            userSchema.setHasschema(false);
        }
        userSchemaService.insert(userSchema);
        return msg;
    }

    @RequestMapping("/findPassword")
    public String findPassword() {
        return "forgot-password";
    }

    @RequestMapping("/resetPassword")
    @ResponseBody
    public Msg resetPassword(String email) {
        User user = userService.selectByEmail(email);
        Msg msg = new Msg();
        if (user == null) {
            msg.setResult("error");
            msg.setMsg("邮箱没有被注册！！！");

            return msg;
        }

        msg.setResult("error");
        msg.setMsg("修改密码的连接已经发送到您的邮箱，请查收！！！");
        return msg;
    }

    private boolean isExists(String username) {
        User user = userService.selectByUserName(username);

        return user != null;
    }

    private boolean emailISExists(String email) {
        User user = userService.selectByEmail(email);
        return user != null;
    }

    private Msg check(User user) {
        Msg msg = new Msg();
        if (StringUtils.isBlank(user.getUsername())) {
            msg.setResult("error");
            msg.setMsg("用户名为空！！！");
            return msg;
        }
        if (isExists(user.getUsername())) {
            msg.setResult("error");
            msg.setMsg("用户名已经被注册！！！");
            return msg;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            msg.setResult("error");
            msg.setMsg("密码为空！！！");
            return msg;
        }
        if (StringUtils.isBlank(user.getEmail())) {
            msg.setResult("error");
            msg.setMsg("邮箱为空！！！");
            return msg;
        }
        if (emailISExists(user.getEmail())) {
            msg.setResult("error");
            msg.setMsg("邮箱已经被注册！！！");
            return msg;
        }

        msg.setResult("success");
        msg.setMsg("注册成功！！！");

        return msg;
    }

    @RequestMapping("/userInfo")
    public String userInfo(Model model) {
        User user = SecurityUserUtils.getPrincipal();
        model.addAttribute("user", user);
        if (user != null) {
            UserSchema userSchema = userSchemaService.selecetByUserId(user.getId());
            model.addAttribute("userSchema", userSchema);
            if (userSchema != null) {
                UserConfig userConfig = redisService.get("user:" + userSchema.getSchemaUserName(), UserConfig.class);
                StringBuilder database = new StringBuilder("");
                userConfig.getSchemas().forEach(schema -> {
                    database.append(schema + ",");
                });
                model.addAttribute("database", database.substring(0, database.length() - 1));
            }
        }

        return "userInfo";
    }

    @RequestMapping("/databaseInfo")
    public String databaseInfo(Model model) {
        User user = SecurityUserUtils.getPrincipal();
        model.addAttribute("user", user);
        List<UserConfig> userConfigs = new ArrayList<>();
        if (user != null) {
            UserSchema userSchema = userSchemaService.selecetByUserId(user.getId());
            model.addAttribute("userSchema", userSchema);
            if (userSchema != null) {
                UserConfig userConfig = redisService.get("user:" + userSchema.getSchemaUserName(), UserConfig.class);
                userConfig.getSchemas().forEach(schema -> {
                    userConfigs.addAll(userConfigService.getAllByName(schema));
                });
            }
        }

        model.addAttribute("userConfigs", userConfigs);

        return "databaseInfo";
    }

    @RequestMapping("/getUsers")
    @ResponseBody
    public Map<String, Object> getUsers(String order, int limit, int offset) {
        List<User> users = userService.selectAll();
        List<Map> result = new ArrayList<>();
        users.forEach(user -> {
            Map<String, Object> entityUser = JSON.parseObject(JSON.toJSONString(user), Map.class);
            UserSchema userSchema = userSchemaService.selecetByUserId(user.getId());
            if (userSchema != null &&
                    StringUtils.isNotBlank(userSchema.getSchemaUserName())) {
                UserConfig userConfig = userConfigService.get("user:" + userSchema.getSchemaUserName());
                List<UserConfig> userConfigs = userConfigService.getAll(userConfig.getSchemas());
                entityUser.put("userNum", userConfigs.size());
                entityUser.put("schema", userConfig.getSchemas());
            }
            result.add(entityUser);
        });
        logger.info("user load finish");

        Map<String, Object> map = new HashMap<>();
        map.put("data", result);
        map.put("total", result.size());
        map.put("page", result.size() / 2 + 1);
        return map;
    }

    @RequestMapping("/users")
    public String users() {
        return "tables";
    }

}
