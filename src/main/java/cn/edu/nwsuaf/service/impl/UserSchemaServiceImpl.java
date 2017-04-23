package cn.edu.nwsuaf.service.impl;

import cn.edu.nwsuaf.dao.UserSchemaMapper;
import cn.edu.nwsuaf.model.UserSchema;
import cn.edu.nwsuaf.service.UserSchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangrongchao on 2017/4/19.
 */
@Service
public class UserSchemaServiceImpl implements UserSchemaService {

    @Autowired
    private UserSchemaMapper userSchemaMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return userSchemaMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserSchema record) {
        return userSchemaMapper.insert(record);
    }

    @Override
    public int insertSelective(UserSchema record) {
        return userSchemaMapper.insertSelective(record);
    }

    @Override
    public UserSchema selectByPrimaryKey(Long id) {
        return userSchemaMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UserSchema record) {
        return userSchemaMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserSchema record) {
        return userSchemaMapper.updateByPrimaryKey(record);
    }

    @Override
    public UserSchema selecetByUserId(long userId) {
        return userSchemaMapper.selecetByUserId(userId);
    }
}
