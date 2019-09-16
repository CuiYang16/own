package com.cy.own.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    //响应码
    private int code;

    //响应文本信息
    private String msg;

    //响应对象信息
    private Object resObj;

    private int count;
    //响应集合
    private List<Object> data;
}
