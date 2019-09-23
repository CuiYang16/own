package com.cy.own.service.user;

import com.cy.own.dto.ResponseDto;
import com.cy.own.entity.user.Users;

public interface UserService {

    ResponseDto registerUser(Users users);

    ResponseDto login(Users users);

    ResponseDto validityUserName(String userName);

    ResponseDto selectAllUser(int page,int limit,String idOrName);

    ResponseDto updataUser(Users users);

    ResponseDto createUser(Users users);
}
