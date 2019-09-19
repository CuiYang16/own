package com.cy.own.web.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserManageController {

    @RequestMapping("/user-manage")
    public String toUserManage() {
        return "/user/userManage";
    }

    @RequestMapping("/user-detail")
    public String toUserDetail() {
        return "/user/userDetail";
    }

    @RequestMapping("/edit-user")
    public String toEditUser() {
        return "/user/editAndAddUser";
    }

    @RequestMapping("/create-user")
    public String toCreateUser() {
        return "/user/editAndAddUser";
    }
}
