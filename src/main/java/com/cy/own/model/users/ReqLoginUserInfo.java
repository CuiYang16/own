package com.cy.own.model.users;

import lombok.Data;

/**
 * @author ：cuiyang
 * @description：TODO
 * @date ：Created in 2020/1/5 13:21
 */
@Data
public class ReqLoginUserInfo {
    /** 用户名 */
    private String userName;

    /** 密码 */
    private String passWord;
}