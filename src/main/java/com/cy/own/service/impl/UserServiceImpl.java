package com.cy.own.service.impl;

import com.cy.own.dao.UsersMapper;
import com.cy.own.dto.ResponseDto;
import com.cy.own.entity.Users;
import com.cy.own.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public ResponseDto login(Users users) {

        if (users.getUserName()!=null) {
            Users loginUser = usersMapper.selectByUserName(users.getUserName());
            if(loginUser.getPassWord()!=null){
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                boolean pwMatches = passwordEncoder.matches(users.getPassWord(), loginUser.getPassWord());
                if (pwMatches) {

                    return ResponseDto.builder().resMsg("登录成功").resCode(21001).build();
                }else{
                    return ResponseDto.builder().resMsg("登录失败，密码错误1").resCode(51001).build();
                }
            }else{
                return ResponseDto.builder().resCode(51001).resMsg("登录失败，用户信息错误，请联系管理员！").build();
            }
        }else {
            return ResponseDto.builder().resCode(51001).resMsg("登录失败，用户名为空！").build();
        }
    }

    @Override
    public ResponseDto registerUser(Users users) {
        if(users.getUserName()!=null&& users.getPassWord()!=null){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            int insert = usersMapper.insert(users);
            return ResponseDto.builder().resMsg(String.valueOf(insert)).resCode(22001).build();
        }

        return ResponseDto.builder().resMsg("注册失败，请正确填写信息！").resCode(52001).build();
    }
}
