package com.cy.own.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class ResponseDto {

    //响应码
    private int resCode;

    //响应文本信息
    private String resMsg;

    //响应对象信息
    private Object resObj;

    //响应集合
    private List<Object> resList;
}
