package com.cy.own.web.rest.role;

import com.cy.own.dto.ResponseDto;
import com.cy.own.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleRest {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/get-roles",method = RequestMethod.GET)
    public ResponseDto getRoles(@RequestParam("page") int page,@RequestParam("limit") int limit){
       return roleService.getRoles(page, limit);
    }
}
