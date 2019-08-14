package com.cy.own.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forward")
public class TestRest {
    @RequestMapping("/button")
    public String testLayui(){
        return "index";
    }
}
