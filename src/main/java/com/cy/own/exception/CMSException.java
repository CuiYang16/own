package com.cy.own.exception;

import com.cy.own.dto.result.ResultCodeEnum;
import lombok.Data;

/**
 * @author ：cuiyang
 * @description：异常返回
 * @date ：Created in 2020/5/3 15:15
 */
@Data
public class CMSException extends RuntimeException {
    private Integer code;

    public CMSException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CMSException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "CMSException{" + "code=" + code + ", message=" + this.getMessage() + '}';
    }
}