package com.cy.own.service.role.impl;

import com.cy.own.dao.role.RoleMapper;
import com.cy.own.dto.ResponseDto;
import com.cy.own.entity.role.Role;
import com.cy.own.entity.role.vo.RoleTreeVo;
import com.cy.own.service.role.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Override
    public ResponseDto getRoles(int page, int limit,String parentId,String idOrName) {

        try {
            PageHelper.startPage(page, limit);
            List<Role> roles = roleMapper.selectAllRoles(parentId,idOrName);
            PageInfo<Role> pageInfo = new PageInfo<>(roles);
            ResponseDto dto = ResponseDto.builder().code(Integer.valueOf(env.getProperty("code.role.select_table_role", "22002")))
                    .msg(env.getProperty("msg.role.select_table_role", "查询成功")).count((int) pageInfo.getTotal())
                    .data(roles).build();
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            ResponseDto dto = ResponseDto.builder().code(Integer.valueOf(env.getProperty("code.role.select_table_role_fail", "52002")))
                    .msg(env.getProperty("msg.role.select_table_role_fail", "查询失败，请刷新重试！")).count(0)
                   .build();
            return dto;
        }
    }
    @Override
    public ResponseDto getRoles() {
        try {
            List<RoleTreeVo> roles = roleMapper.getRoles();
            ResponseDto dto = ResponseDto.builder().code(Integer.valueOf(env.getProperty("code.role.select_tree_role", "22001")))
                    .msg(env.getProperty("msg.role.select_tree_role", "查询成功"))
                    .data(roles).build();
            return dto;
        }catch (Exception e){
            e.printStackTrace();
           return ResponseDto.builder().code(Integer.valueOf(env.getProperty("code.role.select_tree_role_fail", "52001")))
                    .msg(env.getProperty("msg.role.select_tree_role_fail", "树形查讯失败"))
                    .data(null).build();
        }

    }
}
