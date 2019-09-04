package com.cy.own.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forward")
public class LoginController {

    @RequestMapping(value = "/login")
    public String toLogin(){
        return "login";
    };
}
