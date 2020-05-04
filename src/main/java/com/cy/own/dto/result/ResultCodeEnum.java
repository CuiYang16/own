package com.cy.own.dto.result;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ：cuiyang
 * @description：结果状态类
 * @date ：Created in 2020/5/3 15:09
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(true,20000,"成功"),
    USER_LOGIN_SUCCESS(false,21000,"登录成功"),
    USER_LOGOUT_SUCCESS(false,21001,"登出成功"),
    UNKNOWN_ERROR(false,50001,"未知错误"),
    PARAM_ERROR(false,50002,"参数错误"),
    NULL_POINT(false,50003,"空指针异常"),
    HTTP_CLIENT_ERROR(false,50004,"网络异常"),
    USER_NEED_AUTHORITIES(false,51001,"用户登录失败"),
    USER_NO_ACCESS(false,51002,"用户无权访问"),
    USER_NO_LOGIN(false,51003,"用户未登录"),
    XSS_ERROR(false,52001,"输入违法信息")
    ;

    // 响应是否成功
    private Boolean success;
    // 响应状态码
    private Integer code;
    // 响应信息
    private String message;

    ResultCodeEnum(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
