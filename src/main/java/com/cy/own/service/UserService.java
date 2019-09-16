package com.cy.own.service;

import com.cy.own.dto.ResponseDto;
import com.cy.own.entity.Users;

public interface UserService {

    ResponseDto registerUser(Users users);

    ResponseDto login(Users users);

    ResponseDto validityUserName(String userName);
}
