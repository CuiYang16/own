package com.cy.own.service.role.impl;

import com.cy.own.dao.role.RoleMapper;
import com.cy.own.dto.result.ResultInfo;
import com.cy.own.entity.role.Role;
import com.cy.own.entity.role.vo.RoleAddVo;
import com.cy.own.entity.role.vo.RoleTreeVo;
import com.cy.own.service.role.RoleService;
import com.cy.own.util.UUIDutil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cuiyang
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private Environment env;

    private static final String RES_CODE = "code.role.";
    private static final String RES_MSG = "code.msg.";
    Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Override
    public ResultInfo insertRole(RoleAddVo roleAddVo) {
        Role role = Role.builder().roleName(roleAddVo.getRoleName()).description(roleAddVo.getDescription())
                .isDisabled(roleAddVo.getIsDisabled()).roleLevel(roleAddVo.getLevel() + 1)
                .parentId(roleAddVo.getParentId()).roleCode(roleAddVo.getRoleCode()).id(UUIDutil.getUUID()).createTime(new Date()).build();
        if ("same".equalsIgnoreCase(roleAddVo.getAddType())) {
            role.setRoleLevel(roleAddVo.getLevel());
        } else if ("children".equalsIgnoreCase(roleAddVo.getAddType())) {
            role.setRoleLevel(roleAddVo.getLevel() + 1);
        } else {
            return ResultInfo.error().message("插入角色异常！");
        }

        try {
            int insertSelective = roleMapper.insertSelective(role);
            return ResultInfo.ok().message("插入角色成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error().message("插入角色异常！");
        }
    }

    @Override
    public ResultInfo getRoles(int page, int limit, String parentId, String idOrName) {

        try {
            PageHelper.startPage(page, limit);
            List<Role> roles = roleMapper.selectAllRoles(parentId, idOrName);
            PageInfo<Role> pageInfo = new PageInfo<>(roles);

            return ResultInfo.ok().data("count",(int) pageInfo.getTotal()).data("roles",roles).message("查询角色成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error().data("count",0).message("查询角色失败！");
        }
    }

    @Override
    public ResultInfo getRoles() {
        try {
            List<RoleTreeVo> roles = roleMapper.getRoles(null);
            return ResultInfo.ok().data("roles",roles).message("查询树形角色成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error().data("count",0).message("查询树形角色失败！");
        }

    }

    @Override
    public ResultInfo updateRole(Role role) {

        try {
            logger.info(role.toString());
            int update = roleMapper.updateByPrimaryKeySelective(role);
            return ResultInfo.ok().message("更新角色成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error().data("count",0).message("更新角色失败！");
        }

    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public ResultInfo deleteRole(String id) {
        List<RoleTreeVo> roles = roleMapper.getRoles(id);
        List<String> ids = new ArrayList<>();
        ids.add(id);
        this.resursionTree(roles, ids);
        try {
            roleMapper.delRoles(ids);
            return ResultInfo.ok().message("删除角色成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultInfo.error().data("count",0).message("删除角色失败！");
        }

    }

    public void resursionTree(List<RoleTreeVo> roles, List<String> result) {

        for (RoleTreeVo r : roles) {
            result.add(r.getId());
            if (r.getChildren().size() > 0) {
                resursionTree(r.getChildren(), result);
            }
        }
    }
}
