package com.cy.own.service.role.impl;

import com.cy.own.dao.RoleMapper;
import com.cy.own.dto.ResponseDto;
import com.cy.own.entity.role.vo.AllRoleVo;
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
    public ResponseDto getAllRoles(int page,int limit) {
        PageHelper.startPage(1, 999999);
        List<AllRoleVo> allRoleVos = roleMapper.selectRoles();
        PageInfo<AllRoleVo> pageInfo = new PageInfo<AllRoleVo>(allRoleVos);
        ResponseDto dto = ResponseDto.builder().data(allRoleVos).count((int) pageInfo.getTotal())
                .code(Integer.valueOf(env.getProperty("code.role.select_role_all", "22001")))
                .msg(env.getProperty("msg.role.select_role_all", "查询成功！")).build();
        return dto;
    }
}
