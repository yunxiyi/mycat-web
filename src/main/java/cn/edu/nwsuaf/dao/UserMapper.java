package cn.edu.nwsuaf.dao;

import cn.edu.nwsuaf.model.User;

import java.util.List;

/**
 * Created by huangrongchao on 2017/3/26.
 */
public interface UserMapper {
    User selectByUserName(String username);

    long insert(User user);

    int insertSelective(User user);

    int updateByPrimaryKeySelective(User user);

    int updateByPrimaryKey(User user);

    User selectByEmail(String email);

    List<User> selectAll();
}
