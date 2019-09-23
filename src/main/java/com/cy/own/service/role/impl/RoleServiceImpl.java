package com.cy.own.service.role.impl;

import com.cy.own.dao.RoleMapper;
import com.cy.own.dto.ResponseDto;
import com.cy.own.entity.valobj.role.RoleVo;
import com.cy.own.service.role.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private Environment env;


    @Override
    public ResponseDto getRoles(int page, int limit) {

        try {
            PageHelper.startPage(page, limit);
            List<RoleVo> roles = roleMapper.getRoles();
            PageInfo<RoleVo> pageInfo = new PageInfo<>(roles);
            ResponseDto dto = ResponseDto.builder().code(Integer.valueOf(env.getProperty("code.role.select_role", "22001")))
                    .msg(env.getProperty("msg.role.select_role", "查询成功")).count((int) pageInfo.getTotal())
                    .data(roles).build();
            return dto;
        }catch (Exception e){
            ResponseDto dto = ResponseDto.builder().code(Integer.valueOf(env.getProperty("code.role.select_role_fail", "52001")))
                    .msg(env.getProperty("msg.role.select_role_fail", "查询失败，请刷新重试！")).count(0)
                   .build();
            return dto;
        }
    }
}
