package com.cy.own.service.impl;

import com.cy.own.dao.UsersMapper;
import com.cy.own.dto.ResponseDto;
import com.cy.own.entity.Users;
import com.cy.own.service.UserService;
import com.cy.own.util.UUIDutil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Environment env;
    /**
     * 用户登录
     * @param users
     * @return
     */
    @Override
    public ResponseDto login(Users users) {

        if (users.getUserName()!=null) {
            Users loginUser = usersMapper.selectByUserName(users.getUserName());
            if(loginUser.getPassWord()!=null){
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                boolean pwMatches = passwordEncoder.matches(users.getPassWord(), loginUser.getPassWord());
                if (pwMatches) {

                    return ResponseDto.builder().msg("登录成功").code(Integer.valueOf(env.getProperty("user.login_success","21001"))).build();
                }else{
                    return ResponseDto.builder().msg("登录失败，密码错误").code(Integer.valueOf(env.getProperty("user.login_fail","51001"))).build();
                }
            }else{
                return ResponseDto.builder().code(Integer.valueOf(env.getProperty("user.login_fail","51001"))).msg("登录失败，用户信息错误，请联系管理员！").build();
            }
        }else {
            return ResponseDto.builder().code(Integer.valueOf(env.getProperty("user.login_fail","51001"))).msg("登录失败，用户名为空！").build();
        }
    }

    /**
     * 用户注册
     * @param users
     * @return
     */
    @Override
    public ResponseDto registerUser(Users users) {
        if(users.getUserName()!=null&& users.getPassWord()!=null){
           BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            users.setId(UUIDutil.getUUID());
            users.setPassWord(bCryptPasswordEncoder.encode(users.getPassWord()));
            users.setCreateTime(new Date());
            int insert = usersMapper.insertSelective(users);
            return ResponseDto.builder().msg(String.valueOf(insert)).code(Integer.valueOf(env.getProperty("user.register_fail","22001"))).build();
        }

        return ResponseDto.builder().msg("注册失败，请正确填写信息！").code(Integer.valueOf(env.getProperty("user.register_fail","52001"))).build();
    }

    /**
     * 校验用户名是否重复
     * @param userName
     * @return
     */
    @Override
    public ResponseDto validityUserName(String userName) {
        Users users = usersMapper.selectByUserName(userName);
        if(users!=null&& !StringUtils.isBlank(users.getUserName())&&!StringUtils.isBlank(users.getPassWord())){
            ResponseDto dto = ResponseDto.builder().code(Integer.valueOf(env.getProperty("user.validity_username_fail","52002"))).msg("用户名已存在！").build();
            return dto;
        }

        ResponseDto dto = ResponseDto.builder().code(Integer.valueOf(env.getProperty("user.validity_username_success","22002"))).msg("用户名可使用！").build();
        return dto;
    }

    /**
     * 查询所有用户
     * @return
     */
    @Override
    public ResponseDto selectAllUser(int page,int limit) {
        PageHelper.startPage(page, limit);
        List<Users> users = usersMapper.selectAllUser();
        PageInfo<Users> pageInfo = new PageInfo<Users>(users);
        ResponseDto responseDto;
        try {
           responseDto = ResponseDto.builder().data(users).count((int) pageInfo.getTotal()).code(Integer.valueOf(23001)).build();
        }catch (Exception e){
           responseDto = ResponseDto.builder().data(null).count(0).code(Integer.valueOf(53001)).build();
        }
        return responseDto;
    }
}
