package com.cy.own.configuration.security.returnvalue;

import com.alibaba.fastjson.JSON;
import com.cy.own.dto.result.ResultCodeEnum;
import com.cy.own.dto.result.ResultInfo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：cuiyang
 * @description：无权访问
 * @date ：Created in 2020/5/4 12:16
 */

@Component
public class AjaxAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.getWriter().write(JSON.toJSONString(ResultInfo.setResult(ResultCodeEnum.USER_NO_ACCESS)));

    }

}
