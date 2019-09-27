package com.cy.own.service.role.impl;

import com.cy.own.dao.role.RoleMapper;
import com.cy.own.dto.ResponseDto;
import com.cy.own.entity.role.Role;
import com.cy.own.entity.role.vo.RoleAddVo;
import com.cy.own.entity.role.vo.RoleTreeVo;
import com.cy.own.service.role.RoleService;
import com.cy.own.util.UUIDutil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private Environment env;

    private static final String RES_CODE = "code.role.";
    private static final String RES_MSG = "code.msg.";
    Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Override
    public ResponseDto insertRole(RoleAddVo roleAddVo) {
        Role role = Role.builder().roleName(roleAddVo.getRoleName()).description(roleAddVo.getDescription())
                .isDisabled(roleAddVo.getIsDisabled()).roleLevel(roleAddVo.getLevel() + 1)
                .parentId(roleAddVo.getParentId()).roleCode(roleAddVo.getRoleCode()).id(UUIDutil.getUUID()).createTime(new Date()).build();
        if ("same".equalsIgnoreCase(roleAddVo.getAddType())) {
            role.setRoleLevel(roleAddVo.getLevel());
        } else if ("children".equalsIgnoreCase(roleAddVo.getAddType())) {
            role.setRoleLevel(roleAddVo.getLevel() + 1);
        } else {
            return ResponseDto.builder().code(Integer.valueOf(env.getProperty(RES_CODE + "insert_role_fail"), 52003)).msg(env.getProperty(RES_MSG + "insert_role_fail", "新增失败，请刷新重试！"))
                    .build();
        }

        try {
            int insertSelective = roleMapper.insertSelective(role);
            return ResponseDto.builder().code(Integer.valueOf(env.getProperty(RES_CODE + "insert_role", "22003"))).msg(env.getProperty(RES_MSG + "insert_role", "新增成功！"))
                    .resObj(role).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.builder().code(Integer.valueOf(env.getProperty(RES_CODE + "insert_role_fail", "52003"))).msg(env.getProperty(RES_MSG + "insert_role_fail", "新增失败，请刷新重试！"))
                    .build();
        }
    }

    @Override
    public ResponseDto getRoles(int page, int limit, String parentId, String idOrName) {

        try {
            PageHelper.startPage(page, limit);
            List<Role> roles = roleMapper.selectAllRoles(parentId, idOrName);
            PageInfo<Role> pageInfo = new PageInfo<>(roles);
            ResponseDto dto = ResponseDto.builder().code(Integer.valueOf(env.getProperty(RES_CODE + "select_table_role", "22002")))
                    .msg(env.getProperty(RES_MSG + "select_table_role", "查询成功")).count((int) pageInfo.getTotal())
                    .data(roles).build();
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDto dto = ResponseDto.builder().code(Integer.valueOf(env.getProperty(RES_CODE + "select_table_role_fail", "52002")))
                    .msg(env.getProperty(RES_MSG + "select_table_role_fail", "查询失败，请刷新重试！")).count(0)
                    .build();
            return dto;
        }
    }

    @Override
    public ResponseDto getRoles() {
        try {
            List<RoleTreeVo> roles = roleMapper.getRoles();
            ResponseDto dto = ResponseDto.builder().code(Integer.valueOf(env.getProperty(RES_CODE + "select_tree_role", "22001")))
                    .msg(env.getProperty(RES_MSG + "select_tree_role", "查询成功"))
                    .data(roles).build();
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.builder().code(Integer.valueOf(env.getProperty(RES_CODE + "select_tree_role_fail", "52001")))
                    .msg(env.getProperty(RES_MSG + "select_tree_role_fail", "树形查讯失败"))
                    .data(null).build();
        }

    }

    @Override
    public ResponseDto updateRole(Role role) {

        try {
            logger.info(role.toString());
            int update = roleMapper.updateByPrimaryKeySelective(role);
            return ResponseDto.builder().code(Integer.valueOf(env.getProperty(RES_CODE + "update_role", "22004"))).msg(env.getProperty(RES_MSG+"update_role", "编辑角色信息成功！")).build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.builder().code(Integer.valueOf(env.getProperty(RES_CODE + "update_role_fail", "52004"))).msg(env.getProperty(RES_MSG+"update_role_fail", "编辑角色信息失败，请刷新重试！")).build();
        }

    }
}
