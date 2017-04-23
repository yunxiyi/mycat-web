package cn.edu.nwsuaf.service;

import cn.edu.nwsuaf.model.User;
import cn.edu.nwsuaf.model.UserRole;

import java.util.List;

/**
 * Created by huangrongchao on 2017/3/26.
 */
public interface UserRoleService {
    List<UserRole> getRoleByUser(User user);

    int insert(UserRole userRole);

    int insertSelective(UserRole userRole);

    int updateByPrimaryKey(UserRole userRole);

    int updateByPrimaryKeySelective(UserRole userRole);
}
