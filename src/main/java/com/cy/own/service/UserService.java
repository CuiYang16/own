package com.cy.own.service;

import com.cy.own.dto.ResponseDto;
import com.cy.own.entity.Users;

public interface UserService {

    ResponseDto registerUser(Users users);

    ResponseDto login(Users users);

    ResponseDto validityUserName(String userName);

    ResponseDto selectAllUser(int page,int limit);

    ResponseDto updataUser(Users users);

    ResponseDto createUser(Users users);
}
