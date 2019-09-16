package com.cy.own.web.rest;

import com.cy.own.dto.ResponseDto;
import com.cy.own.entity.Users;
import com.cy.own.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginRest {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(LoginRest.class);

    @RequestMapping(value="/user-login",method = RequestMethod.POST)
    public ResponseDto login(Users users){
        System.out.println(users.toString());
        return userService.login(users);
    }

    @RequestMapping(value = "/user-register",method = RequestMethod.POST)
    public ResponseDto register(Users users){
        logger.info(users.toString());
        return userService.registerUser(users);
    }
}
