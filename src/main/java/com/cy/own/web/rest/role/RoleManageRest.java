package com.cy.own.web.rest.role;

import com.cy.own.dto.ResponseDto;
import com.cy.own.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleManageRest {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/get-roles",method = RequestMethod.GET)
    public ResponseDto getAllRole(){
        return roleService.getAllRoles(0,0);
    }
}
