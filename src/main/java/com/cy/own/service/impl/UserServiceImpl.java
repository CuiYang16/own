package com.cy.own.service.impl;

import com.cy.own.dao.UsersMapper;
import com.cy.own.dto.ResponseDto;
import com.cy.own.entity.Users;
import com.cy.own.service.UserService;
import com.cy.own.util.UUIDutil;
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

                    return ResponseDto.builder().msg("登录成功").code(21001).build();
                }else{
                    return ResponseDto.builder().msg("登录失败，密码错误1").code(51001).build();
                }
            }else{
                return ResponseDto.builder().code(51001).msg("登录失败，用户信息错误，请联系管理员！").build();
            }
        }else {
            return ResponseDto.builder().code(51001).msg("登录失败，用户名为空！").build();
        }
    }

    @Override
    public ResponseDto registerUser(Users users) {
        if(users.getUserName()!=null&& users.getPassWord()!=null){
           BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            users.setId(UUIDutil.getUUID());
            users.setPassWord(bCryptPasswordEncoder.encode(users.getPassWord()));
            int insert = usersMapper.insertSelective(users);
            return ResponseDto.builder().msg(String.valueOf(insert)).code(22001).build();
        }

        return ResponseDto.builder().msg("注册失败，请正确填写信息！").code(52001).build();
    }
}
