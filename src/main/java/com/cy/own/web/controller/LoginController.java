package com.cy.own.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/forward")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login")
    public String toLogin(Model model, HttpServletRequest request) {
        //SPRING_SECURITY_LAST_EXCEPTION为默认异常储存在session中的名字（SimpleUrlAuthenticationFailureHandler）
        Exception e = (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        String exceptionName;
        String error;
        if (e != null && e.toString() != null) {
            logger.info("e：" + e.toString());
            logger.info("exceptionName：" + e.getMessage());
            //截取异常名称
            exceptionName = e.getMessage();

            //返回错误信息
            model.addAttribute("error", exceptionName);
        }

        return "login";
    }

    ;

    @RequestMapping("/to-login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/to-register")
    public String toRegister() {
        return "register";
    }

    @RequestMapping(value = "/index")
    public String toIndex() {
        return "index";
    };

    @RequestMapping("/home")
    public String toHome(){
        return "home";
    }
}
