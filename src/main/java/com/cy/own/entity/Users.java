package com.cy.own.entity;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    public Users(String userName, String passWord, Boolean sex, Date birthday) {
        this.userName = userName;
        this.passWord = passWord;
        this.sex = sex;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}