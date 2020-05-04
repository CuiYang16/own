package com.cy.own.service.role;

import com.cy.own.dto.result.ResultInfo;
import com.cy.own.entity.role.Role;
import com.cy.own.entity.role.vo.RoleAddVo;

public interface RoleService {
    //table
    ResultInfo getRoles(int page, int limit, String parentId, String idOrName);
    //tree
    ResultInfo getRoles();

    ResultInfo insertRole(RoleAddVo roleAddVo);

    ResultInfo updateRole(Role role);

    ResultInfo deleteRole(String id);
}

