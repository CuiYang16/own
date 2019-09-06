package com.cy.own.entity;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Users {
    /**
     *
     */
    private String id;

    /**
     *
     */
    private String userName;

    /**
     *
     */
    private String passWord;

    /**
     *
     */
    private Boolean sex;

    /**
     *
     */
    private Date birthday;

    /**
     *
     */
    private Boolean enabled;

    /**
     *
     */
    private Boolean accountNonLocked;

    /**
     *
     */
    private Boolean credentialsNonExpired;

    /**
     *
     */
    private Boolean accountNonExpired;



    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}