package com.cy.own.dao.user;

import com.cy.own.entity.user.Users;

import java.util.List;

public interface UsersMapper {
    int deleteByPrimaryKey(String id);

    int insert(Users record);

    int insertSelective(Users record);
//验证用户
    Users selectByUserName(String userName);
    //查询所有用户
    List<Users> selectAllUser(String idOrName);

    Users selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);
//增加登录次数
    int addLoginCount(String userName);
}