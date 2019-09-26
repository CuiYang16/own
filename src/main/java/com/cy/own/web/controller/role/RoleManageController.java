package com.cy.own.web.controller.role;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/roles")
public class RoleManageController {


    @RequestMapping("role-manage")
    public String toRoleManage(){
        return "/role/roleManage";
    }

    @RequestMapping("edit-role")
    public String toEditRole(){
        return "/role/editAndAddRole";
    }

    @RequestMapping("create-role")
    public String toAddRole(){
        return "/role/editAndAddRole";
    }


}
