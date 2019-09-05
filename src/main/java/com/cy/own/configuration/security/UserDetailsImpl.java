package com.cy.own.configuration.security;

import com.cy.own.dao.PermissionMapper;
import com.cy.own.dao.RoleMapper;
import com.cy.own.dao.UsersMapper;
import com.cy.own.entity.Permission;
import com.cy.own.entity.Role;
import com.cy.own.entity.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    private Logger logger = LoggerFactory.getLogger(UserDetailsImpl.class);
    //private static Log logger = LogFactory.getLog(UserDetailsImpl.class);
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users users = usersMapper.selectByUserName(s);
        if (users!=null&&users.getId()!=null) {
            List<Permission> permissions = permissionMapper.selectByUserId(users.getId());
            List<Role> roles = roleMapper.selectByUserId(users.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            roles.forEach(role -> {
                if (role!=null&&role.getRoleName()!=null) {
                    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
                }
            });
            permissions.forEach(permission -> {
                if (permission!=null&&permission.getName()!=null) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
                }
            });
           logger.info(users.getUserName()+":登录成功");
            logger.debug(users.getUserName()+":debug登录成功");
            return new User(users.getUserName(), users.getPassWord(), grantedAuthorities);
        }
        throw new UsernameNotFoundException(users.getUserName() + " do not exist!");
    }

    // 密码加密方式
    @Bean("bCryptPasswordEncoder")
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
