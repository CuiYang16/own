package com.cy.own.service.user;

import com.cy.own.dto.result.ResultInfo;
import com.cy.own.entity.user.Users;
import com.cy.own.model.users.ReqLoginUserInfo;

public interface UserService {

    ResultInfo registerUser(Users users);

    ResultInfo login(ReqLoginUserInfo reqLogin);

    ResultInfo validityUserName(String userName);

    ResultInfo selectAllUser(int page,int limit,String idOrName);

    ResultInfo updataUser(Users users);

    ResultInfo createUser(Users users);
}
