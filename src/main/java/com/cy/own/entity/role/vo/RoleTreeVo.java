package com.cy.own.entity.role.vo;

import com.cy.own.entity.role.Role;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * @author cuiyang
 */
@Data
public class RoleTreeVo extends Role {

    private List<RoleTreeVo> children;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
