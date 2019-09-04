package com.cy.own.configuration.xss;


import com.cy.own.common.xss.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.ServletException;

@Configuration
public class XssConfig extends WebMvcConfigurationSupport {
    @Bean
    public FilterRegistrationBean addFilter() throws ServletException {
        XssFilter xssFilter = new XssFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(xssFilter);
        registrationBean.setName("xssFilter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(900);
        return registrationBean;
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        //不拦截静态资源，与securityConfig结合使用
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
