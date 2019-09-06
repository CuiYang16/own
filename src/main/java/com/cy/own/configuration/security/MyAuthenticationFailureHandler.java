package com.cy.own.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final static String SPRING_SECURITY_EXCEPTION = "SPRING_SECURITY_EXCEPTION";
    private String exceptionName;
    @Autowired
    private ObjectMapper mapper;

    private Logger logger = LoggerFactory.getLogger(MyAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //httpServletRequest.getSession().setAttribute(SPRING_SECURITY_EXCEPTION, null);
        //// httpServletResponse.getWriter().write(mapper.writeValueAsString(e.getMessage()));
        //
        exceptionName=e.toString().substring(e.toString().lastIndexOf(".")+1,e.toString().lastIndexOf(":")==-1?e.toString().length():e.toString().lastIndexOf(":"));
        //logger.info(exceptionName);
        //switch (exceptionName) {
        //    case "BadCredentialsException":
        //        httpServletRequest.getSession().setAttribute(SPRING_SECURITY_EXCEPTION, "密码错误！");
        //    case "InternalAuthenticationServiceException":
        //        httpServletRequest.getSession().setAttribute(SPRING_SECURITY_EXCEPTION, "账号不存在！");
        //
        //
        //}
        httpServletRequest.getRequestDispatcher("/forward/login");
        //httpServletResponse.sendRedirect("/forward/login?error="+mapper.writeValueAsString(exceptionName));
    }


}
