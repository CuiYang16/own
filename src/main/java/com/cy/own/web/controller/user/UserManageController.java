package com.cy.own.web.controller.user;

import com.cy.own.dto.result.ResultInfo;
import com.cy.own.model.users.ReqLoginUserInfo;
import com.cy.own.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users")
public class UserManageController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultInfo userLogin(@RequestBody ReqLoginUserInfo reqLoginUserInfo){
        return userService.login(reqLoginUserInfo);
    }

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
