package cn.edu.nwsuaf.service;

import cn.edu.nwsuaf.model.UserSchema;

/**
 * Created by huangrongchao on 2017/4/19.
 */
public interface UserSchemaService {
    int deleteByPrimaryKey(Long id);

    int insert(UserSchema record);

    int insertSelective(UserSchema record);

    UserSchema selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserSchema record);

    int updateByPrimaryKey(UserSchema record);

    UserSchema selecetByUserId(long userId);
}
