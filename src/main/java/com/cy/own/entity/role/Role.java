package com.cy.own.entity.role;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
/**
 * @author cuiyang
 */


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role{
    /**
     * 
     */
    private String id;

    /**
     * 角色名
     */
    private String roleName;

    private String roleCode;

    private int roleLevel;
    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;



    private Boolean isDisabled;

    /**
     * 父角色id
     */
    private String parentId;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }


    public String getAuthority() {
        return this.roleName;
    }

}