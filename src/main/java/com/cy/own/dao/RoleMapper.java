package com.cy.own.dao;

import com.cy.own.entity.role.Role;
import com.cy.own.entity.valobj.role.RoleVo;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByUserId(String userId);

    Role selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<RoleVo> getRoles();
}