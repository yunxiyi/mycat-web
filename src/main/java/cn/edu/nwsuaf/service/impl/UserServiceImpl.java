package cn.edu.nwsuaf.service.impl;

import cn.edu.nwsuaf.dao.UserMapper;
import cn.edu.nwsuaf.model.User;
import cn.edu.nwsuaf.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangrongchao on 2017/3/26.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    Logger log = Logger.getLogger(UserServiceImpl.class);

    @Override
    public User selectByUserName(String username) {
        User user = userMapper.selectByUserName(username);
        return user;
    }

    @Override
    public long insert(User user) {
        log.info("insert username : " + user.getUsername());
        return userMapper.insert(user);
    }

    @Override
    public int insertSelective(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int updateByPrimaryKeySelective(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int updateByPrimaryKey(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public User selectByEmail(String s) {
        return userMapper.selectByEmail(s);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }
}
