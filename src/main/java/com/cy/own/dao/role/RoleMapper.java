package com.cy.own.dao.role;

import com.cy.own.entity.role.Role;
import com.cy.own.entity.role.vo.RoleTreeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(String id);

    int insertSelective(Role record);

    List<Role> selectByUserId(String userId);

    Role selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Role record);

    List<RoleTreeVo> getRoles(String id);

    List<Role> selectAllRoles(@Param("parentId") String parentId, @Param("idOrName") String idOrName);

    int delRoles(List<String> ids);
}