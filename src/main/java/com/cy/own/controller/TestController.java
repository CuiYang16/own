package com.cy.own.controller;

import com.cy.own.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping("/insert")
    public void test1(){
        this.mongoTemplate.insert(new User("111", "2222"));
    }
}
