package com.cy.own.service.role;

import com.cy.own.dto.ResponseDto;

public interface RoleService {
    ResponseDto getRoles(int page, int limit);
}

