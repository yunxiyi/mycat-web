package cn.edu.nwsuaf.dao;

import cn.edu.nwsuaf.model.UserSchema;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserSchemaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserSchema record);

    int insertSelective(UserSchema record);

    UserSchema selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserSchema record);

    int updateByPrimaryKey(UserSchema record);

    UserSchema selecetByUserId(Long userId);
}