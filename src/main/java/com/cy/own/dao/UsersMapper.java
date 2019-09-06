package com.cy.own.dao;

import com.cy.own.entity.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper {
    int insert(Users record);
    Users selectByUserName(String userName);
    int insertSelective(Users record);
}