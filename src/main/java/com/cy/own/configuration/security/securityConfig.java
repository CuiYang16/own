package com.cy.own.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class securityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsImpl userDetailsImpl;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        auth.userDetailsService(userDetailsImpl).passwordEncoder(passwordEncoder());

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //super.configure(web);
        //放弃拦截静态资源
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http.formLogin().loginPage("/forward/login")
                //.loginProcessingUrl("/user/user-login")
                .usernameParameter("userName")
                .passwordParameter("passWord")

                //成功跳转路径必须真实存在
                //.successForwardUrl("/forward/index")
                .successHandler(myAuthenticationSuccessHandler)
                //失败跳转路径必须真实存在
                .failureUrl("/forward/login").permitAll();
        //不需要验证
        http.authorizeRequests().antMatchers("/favicon.ico").permitAll()
                .anyRequest().authenticated();

        http.logout().logoutUrl("/forward/logout").logoutSuccessUrl("/forward/login").deleteCookies("JSESSIONID").permitAll()
                .and().csrf().disable();
        http.sessionManagement().invalidSessionUrl("/forward/login").maximumSessions(5);
        http.rememberMe().key("own-rememberme").rememberMeCookieName("own-remwmber-me-cookie").tokenValiditySeconds(60);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
