package com.cy.own.web.rest.user;

import com.cy.own.dto.ResponseDto;
import com.cy.own.entity.user.Users;
import com.cy.own.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserManageRest {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/get-users",method = RequestMethod.GET)
    public ResponseDto getAllUser(@RequestParam("page")int page,@RequestParam("limit")int limit,@RequestParam(value = "idOrName",required = false) String idOrName){
        return userService.selectAllUser(page, limit,idOrName);
    }

    @RequestMapping(value = "/update-user",method = RequestMethod.POST)
    public ResponseDto updataUser(Users users){

        return userService.updataUser(users);
    }

    @RequestMapping(value = "/insert-user",method = RequestMethod.POST)
    public ResponseDto insertUser(Users users){

        return userService.createUser(users);
    }
}
