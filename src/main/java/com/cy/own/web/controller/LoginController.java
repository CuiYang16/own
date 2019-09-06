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
            //截取异常名称
            exceptionName = e.toString()
                    .substring(e.toString().lastIndexOf(".") + 1,
                            e.toString().lastIndexOf(":") == -1 ? e.toString().length() : e.toString().lastIndexOf(":"));
            logger.info("exceptionName：" + exceptionName);
            //判断异常名称
            switch (exceptionName) {
                case "BadCredentialsException":
                    error = "密码错误！";
                    break;
                case "InternalAuthenticationServiceException":
                    error = "账号不存在！";
                    break;
                case "LockedException":
                    error = "账户已锁定！";
                    break;
                case "DisabledException":
                    error = "账户被禁用！";
                    break;
                case "CredentialExpiredException":
                    error = "证书过期！";
                    break;
                case "AccountExpiredException ":
                    error = "账户过期";
                    break;
                case "UsernameNotFoundException":
                    error = "用户名不存在！";
                    break;
                default:
                    error = "";
                    break;

            }
            //返回错误信息
            model.addAttribute("error", error);
        }

        return "login";
    }

    ;

    @RequestMapping(value = "/index")
    public String toindex() {
        return "index";
    }

    ;
}
