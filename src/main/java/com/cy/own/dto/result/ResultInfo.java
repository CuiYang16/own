package com.cy.own.dto.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：cuiyang
 * @description：返回结果
 * @date ：Created in 2020/5/3 15:10
 */
@Data
public class ResultInfo {
    private Boolean success;

    private Integer code;

    private String message;

    private Map<String, Object> data = new HashMap<>();

    // 构造器私有
    private ResultInfo(){}

    // 通用返回成功
    public static ResultInfo ok() {
        ResultInfo r = new ResultInfo();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    // 通用返回失败，未知错误
    public static ResultInfo error() {
        ResultInfo r = new ResultInfo();
        r.setSuccess(ResultCodeEnum.UNKNOWN_ERROR.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        r.setMessage(ResultCodeEnum.UNKNOWN_ERROR.getMessage());
        return r;
    }

    // 设置结果，形参为结果枚举
    public static ResultInfo setResult(ResultCodeEnum result) {
        ResultInfo r = new ResultInfo();
        r.setSuccess(result.getSuccess());
        r.setCode(result.getCode());
        r.setMessage(result.getMessage());
        return r;
    }

    /**------------使用链式编程，返回类本身-----------**/

    // 自定义返回数据
    public ResultInfo data(Map<String,Object> map) {
        this.setData(map);
        return this;
    }

    // 通用设置data
    public ResultInfo data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    // 自定义状态信息
    public ResultInfo message(String message) {
        this.setMessage(message);
        return this;
    }

    // 自定义状态码
    public ResultInfo code(Integer code) {
        this.setCode(code);
        return this;
    }

    // 自定义返回结果
    public ResultInfo success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
}
