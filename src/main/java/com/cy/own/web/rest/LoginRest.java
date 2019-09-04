package com.cy.own.web.rest;

import com.cy.own.dto.ResponseDto;
import com.cy.own.entity.Users;
import com.cy.own.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/index")
public class LoginRest {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/user-login",method = RequestMethod.GET)
    public ResponseDto login(Users users){

        return userService.login(users);
    }

}
