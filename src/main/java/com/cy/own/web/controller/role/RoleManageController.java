package com.cy.own.web.controller.role;

import com.cy.own.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/roles")
public class RoleManageController {


    @RequestMapping("role-manage")
    public String toRoleManage(){
        return "/role/roleManage";
    }

}
