package com.cy.own.configuration.security.returnvalue;

import com.alibaba.fastjson.JSON;
import com.cy.own.dto.result.ResultCodeEnum;
import com.cy.own.dto.result.ResultInfo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：cuiyang
 * @description：用户未登录时返回给前端的数据
 * @date ：Created in 2020/5/4 12:22
 */

@Component
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.getWriter().write(JSON.toJSONString(ResultInfo.setResult(ResultCodeEnum.USER_NO_LOGIN)));
    }

}
