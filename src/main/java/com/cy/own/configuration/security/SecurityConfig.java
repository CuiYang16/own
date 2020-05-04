package com.cy.own.configuration.security;

import com.alibaba.druid.pool.DruidDataSource;
import com.cy.own.configuration.security.returnvalue.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    AjaxAuthenticationEntryPoint authenticationEntryPoint;//未登陆时返回 JSON 格式的数据给前端（否则为 html）

    //@Autowired
    //AjaxAuthenticationSuccessHandler authenticationSuccessHandler; //登录成功返回的 JSON 格式数据给前端（否则为 html）

    @Autowired
    AjaxAuthenticationFailureHandler authenticationFailureHandler; //登录失败返回的 JSON 格式数据给前端（否则为 html）

    @Autowired
    AjaxLogoutSuccessHandler logoutSuccessHandler;//注销成功返回的 JSON 格式数据给前端（否则为 登录时的 html）

    @Autowired
    AjaxAccessDeniedHandler accessDeniedHandler;//无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）

    @Autowired
    private UserDetailsImpl userDetailsImpl;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private DruidDataSource dataSource;

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
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/non-auth/**").hasAnyRole("ADMIN", "USER").and()
                .formLogin().loginProcessingUrl("/users/login")

                //成功跳转路径必须真实存在
                //.successForwardUrl("/forward/index")
                .successHandler(myAuthenticationSuccessHandler)
                //失败跳转路径必须真实存在
                .failureHandler(authenticationFailureHandler)
                //.failureUrl("/forward/login")
                .permitAll();
        //不需要验证
        http.authorizeRequests().antMatchers("/favicon.ico", "/user/user-register", "/forward/to-login", "/forward/to-register"
                , "/user/val-user-name").permitAll()
                .anyRequest().authenticated();

        http.logout().logoutUrl("/user/logout").logoutSuccessUrl("/user/login").logoutSuccessHandler(logoutSuccessHandler).deleteCookies("JSESSIONID").permitAll()
                .and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.sessionManagement().invalidSessionUrl("/forward/login").maximumSessions(5);
        http.rememberMe().key("own-remember-me").rememberMeCookieName("own-remember-me-cookie")
                .tokenValiditySeconds(60 * 60 * 24 * 7).tokenRepository(persistentTokenRepository());
        http.headers().frameOptions().disable();
        // 无权访问 JSON 格式的数据
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {

        MyJdbcTokenRepositoryImpl persistentTokenRepository = new MyJdbcTokenRepositoryImpl();
        //JdbcTokenRepositoryImpl persistentTokenRepository = new JdbcTokenRepositoryImpl();
        // 将 DataSource 设置到 PersistentTokenRepository
        persistentTokenRepository.setDataSource(dataSource);
        // 第一次启动的时候自动建表（可以不用这句话，自己手动建表，源码中有语句的）
        // persistentTokenRepository.setCreateTableOnStartup(true);
        return persistentTokenRepository;
    }

}
