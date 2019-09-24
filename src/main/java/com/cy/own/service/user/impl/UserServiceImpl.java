package com.cy.own.service.user.impl;

import com.cy.own.dao.user.UsersMapper;
import com.cy.own.dto.ResponseDto;
import com.cy.own.entity.user.Users;
import com.cy.own.service.user.UserService;
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

                    return ResponseDto.builder().msg("登录成功").code(Integer.valueOf(env.getProperty("code.user.login_success","21001"))).build();
                }else{
                    return ResponseDto.builder().msg("登录失败，密码错误").code(Integer.valueOf(env.getProperty("code.user.login_fail","51001"))).build();
                }
            }else{
                return ResponseDto.builder().code(Integer.valueOf(env.getProperty("code.user.login_fail","51001"))).msg("登录失败，用户信息错误，请联系管理员！").build();
            }
        }else {
            return ResponseDto.builder().code(Integer.valueOf(env.getProperty("code.user.login_fail","51001"))).msg("登录失败，用户名为空！").build();
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
            return ResponseDto.builder().msg(String.valueOf(insert)).code(Integer.valueOf(env.getProperty("code.user.register_fail","21002"))).build();
        }

        return ResponseDto.builder().msg("注册失败，请正确填写信息！").code(Integer.valueOf(env.getProperty("code.user.register_fail","51002"))).build();
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
            ResponseDto dto = ResponseDto.builder().code(Integer.valueOf(env.getProperty("code.user.validity_username_fail","51003"))).msg("用户名已存在！").build();
            return dto;
        }

        ResponseDto dto = ResponseDto.builder().code(Integer.valueOf(env.getProperty("code.user.validity_username_success","21003"))).msg("用户名可使用！").build();
        return dto;
    }

    /**
     * 查询所有用户
     * @return
     */
    @Override
    public ResponseDto selectAllUser(int page,int limit,String idOrName) {
        PageHelper.startPage(page, limit);
        List<Users> users = usersMapper.selectAllUser(idOrName);
        PageInfo<Users> pageInfo = new PageInfo<Users>(users);
        ResponseDto responseDto;
        try {
           responseDto = ResponseDto.builder().data(users).count((int) pageInfo.getTotal()).code(Integer.valueOf(env.getProperty("code.user.select_user_all","21004"))).build();
        }catch (Exception e){
           responseDto = ResponseDto.builder().data(null).count(0).code(Integer.valueOf(env.getProperty("code.user.select_user_all_fail","51004"))).build();
        }
        return responseDto;
    }

    /**
     * 更新用户
     * @param users
     * @return
     */
    @Override
    public ResponseDto updataUser(Users users) {
        try {
            int update = usersMapper.updateByPrimaryKeySelective(users);
            ResponseDto responseDto = ResponseDto.builder().code(Integer.valueOf(env.getProperty("code.user.update_user", "21005"))).msg(env.getProperty("msg.user.update_user", "编辑用户信息成功！")).build();
            return responseDto;
        }catch (Exception e){
            ResponseDto responseDto = ResponseDto.builder().code(Integer.valueOf(env.getProperty("code.user.update_user_fail", "51005"))).msg(env.getProperty("msg.user.update_user_fail", "编辑用户信息失败，请刷新重试！")).build();
            return responseDto;
        }

    }

    /**
     * 创建用户
     * @param users
     * @return
     */
    @Override
    public ResponseDto createUser(Users users) {

        try{
            Users user = usersMapper.selectByUserName(users.getUserName());
            if(user!=null&& !StringUtils.isBlank(user.getUserName())&&!StringUtils.isBlank(user.getPassWord())){
                ResponseDto dto = ResponseDto.builder().code(Integer.valueOf(env.getProperty("code.user.validity_username_fail","51003"))).msg("用户名已存在！").build();
                return dto;
            }
            users.setCreateTime(new Date());
            users.setId(UUIDutil.getUUID());
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            users.setPassWord(bCryptPasswordEncoder.encode(env.getProperty("user.default_pwd")));
            int insertSelective = usersMapper.insertSelective(users);
            ResponseDto responseDto = ResponseDto.builder().code(Integer.valueOf(env.getProperty("code.user.insert_user", "21006"))).msg(env.getProperty("msg.user.insert_user", "添加用户成功！")).build();
            return responseDto;
        }catch (Exception e){
            ResponseDto responseDto =  ResponseDto.builder().code(Integer.valueOf(env.getProperty("code.user.insert_user_fail", "51006"))).msg(env.getProperty("msg.user.insert_user_fail", "添加用户失败，请刷新重试！")).build();
            return responseDto;
        }
    }
}
