package cn.edu.nwsuaf.service.impl;

import cn.edu.nwsuaf.dao.UserRoleMapper;
import cn.edu.nwsuaf.model.User;
import cn.edu.nwsuaf.model.UserRole;
import cn.edu.nwsuaf.service.UserRoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangrongchao on 2017/3/26.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    Logger log = Logger.getLogger(UserServiceImpl.class);

    @Override
    public List<UserRole> getRoleByUser(User user) {
        return userRoleMapper.selectRolesByUserId(user.getId());
    }

    @Override
    public int insert(UserRole userRole) {
        log.info("insert userRole by userId is : " + userRole.getUserid());
        return userRoleMapper.insert(userRole);
    }

    @Override
    public int insertSelective(UserRole userRole) {
        return userRoleMapper.insertSelective(userRole);
    }

    @Override
    public int updateByPrimaryKey(UserRole userRole) {
        return userRoleMapper.updateByPrimaryKey(userRole);
    }

    @Override
    public int updateByPrimaryKeySelective(UserRole userRole) {
        return userRoleMapper.updateByPrimaryKeySelective(userRole);
    }
}
