package com.cy.own.configuration.security.returnvalue;

import com.alibaba.fastjson.JSON;
import com.cy.own.dto.result.ResultCodeEnum;
import com.cy.own.dto.result.ResultInfo;
import com.cy.own.entity.user.Users;
import com.cy.own.util.token.JwtTokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：cuiyang
 * @description：用户登录成功时返回给前端的数据
 * @date ：Created in 2020/5/4 12:24
 */


public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Users users = (Users) authentication.getPrincipal();

        String jwtToken = JwtTokenUtil.generateToken((UserDetails) users);

        httpServletResponse.getWriter().write(JSON.toJSONString(ResultInfo.setResult(ResultCodeEnum.USER_LOGIN_SUCCESS)));

    }
}
