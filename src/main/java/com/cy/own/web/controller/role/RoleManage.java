package com.cy.own.web.controller.role;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/roles")
public class RoleManage {

    @RequestMapping("role-manage")
    public String toRoleManage(){
        return "/role/roleManage";
    }

}
