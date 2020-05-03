package com.cy.own.service.role;

import com.cy.own.entity.role.Role;
import com.cy.own.entity.role.vo.RoleAddVo;

public interface RoleService {
    //table
    ResponseDto getRoles(int page, int limit,String parentId,String idOrName);
    //tree
    ResponseDto getRoles();

    ResponseDto insertRole(RoleAddVo roleAddVo);

    ResponseDto updateRole(Role role);

    ResponseDto deleteRole(String id);
}

