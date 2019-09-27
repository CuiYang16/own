package com.cy.own.web.rest.role;

import com.cy.own.dto.ResponseDto;
import com.cy.own.entity.role.Role;
import com.cy.own.entity.role.vo.RoleAddVo;
import com.cy.own.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleManageRest {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/get-tree-roles",method = RequestMethod.GET)
    public ResponseDto getTreeRole(){
        return roleService.getRoles();
    }

    @RequestMapping(value = "/get-table-roles",method = RequestMethod.GET)
    public ResponseDto getTableRole(int page,int limit,@RequestParam(value = "parentId",required = false)String parentId,@RequestParam(value = "idOrName",required = false)String idOrName){
        return roleService.getRoles(page,limit,parentId,idOrName);
    }

    @RequestMapping(value = "/insert-role", method = RequestMethod.POST)
    public ResponseDto createRole(RoleAddVo roleAddVo){
        return roleService.insertRole(roleAddVo);
    }
    @RequestMapping(value = "/update-role", method = RequestMethod.POST)
    public ResponseDto updateRole(Role role){
        return roleService.updateRole(role);
    }

}
