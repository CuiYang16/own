package com.cy.own.dto.result;

import lombok.Getter;

/**
 * @author ：cuiyang
 * @description：结果状态类
 * @date ：Created in 2020/5/3 15:09
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(true,20000,"成功"),
    UNKNOWN_ERROR(false,50001,"未知错误"),,
    PARAM_ERROR(false,50002,"参数错误"),
    NULL_POINT(false,50003,"空指针异常"),
    HTTP_CLIENT_ERROR(false,50004,"网络异常")
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
