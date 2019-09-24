package com.cy.own.service.role;

import com.cy.own.dto.ResponseDto;

public interface RoleService {
    //table
    ResponseDto getRoles(int page, int limit,String parentId,String idOrName);
    //tree
    ResponseDto getRoles();
}

