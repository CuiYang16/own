package com.cy.own.service.user.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.cy.own.dao.user.UsersMapper;
import com.cy.own.dto.result.ResultInfo;
import com.cy.own.entity.user.Users;
import com.cy.own.model.users.ReqLoginUserInfo;
import com.cy.own.service.user.UserService;
import com.cy.own.util.UUIDutil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Environment env;

    /**
     * 用户登录
     *
     * @param reqLogin
     * @return
     */
    @Override
    public ResultInfo login(ReqLoginUserInfo reqLogin) {

        if(ObjectUtil.isNull(reqLogin)){
            return ResultInfo.error().message("登录失败,确认输入信息后重试！");
        }

        if (!StrUtil.hasBlank(reqLogin.getUserName()) && !StrUtil.hasBlank(reqLogin.getPassWord())) {
            Users loginUser = usersMapper.selectByUserName(reqLogin.getUserName());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean pwMatches = passwordEncoder.matches(loginUser.getPassWord(), reqLogin.getPassWord());
            if (pwMatches && loginUser.getEnabled()) {
                if (loginUser.getAccountNonLocked()) {
                    return ResultInfo.error().message("用户已锁定！");
                }
                if (loginUser.getAccountNonExpired()) {
                    return ResultInfo.error().message("用户授权到期！");
                }
                if (loginUser.getCredentialsNonExpired()) {
                    return ResultInfo.error().message("用户凭证到期！");
                }
                return ResultInfo.ok().message("登录成功！").data("token", "");
            }

        }
            return ResultInfo.error().message("登录失败！");
    }

    /**
     * 用户注册
     *
     * @param users
     * @return
     */
    @Override
    public ResultInfo registerUser(Users users) {
        return ResultInfo.ok();
    }

    /**
     * 校验用户名是否重复
     *
     * @param userName
     * @return
     */
    @Override
    public ResultInfo validityUserName(String userName) {
        return ResultInfo.ok();
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public ResultInfo selectAllUser(int page, int limit, String idOrName) {
        PageHelper.startPage(page, limit);
        return ResultInfo.ok();
    }

    /**
     * 更新用户
     *
     * @param users
     * @return
     */
    @Override
    public ResultInfo updataUser(Users users) {
        return ResultInfo.ok();

    }

    /**
     * 创建用户
     *
     * @param users
     * @return
     */
    @Override
    public ResultInfo createUser(Users users) {
        return ResultInfo.ok();

    }

    public UsersMapper getUsersMapper() {
        return usersMapper;
    }

    public void setUsersMapper(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }
}
