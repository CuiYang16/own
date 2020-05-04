package com.cy.own.entity.role.vo;

import lombok.*;

import java.io.Serializable;

/**
 * @author cuiyang
 */
@Data
@Builder
public class RoleAddVo implements Serializable {

    private static final long serialVersionUID = 3480443304003287049L;

    private String roleName;

    private Boolean isDisabled;
    private String description;

    private String roleCode;
    private int level;

    private String parentId;

    private String addType;


}
