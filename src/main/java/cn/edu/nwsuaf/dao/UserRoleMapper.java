package cn.edu.nwsuaf.dao;

import cn.edu.nwsuaf.model.UserRole;

import java.util.List;

/**
 * Created by huangrongchao on 2017/3/26.
 */
public interface UserRoleMapper {
    List<UserRole> selectRolesByUserId(Long userId);

    int insert(UserRole userRole);

    int insertSelective(UserRole userRole);

    int updateByPrimaryKey(UserRole userRole);

    int updateByPrimaryKeySelective(UserRole userRole);
}
