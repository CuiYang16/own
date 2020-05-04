package com.cy.own.model.users;

import lombok.Data;

/**
 * @author ：cuiyang
 * @description：TODO
 * @date ：Created in 2020/1/5 13:21
 */

public class ReqLoginUserInfo {
    /** 用户名 */
    private String userName;

    /** 密码 */
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}