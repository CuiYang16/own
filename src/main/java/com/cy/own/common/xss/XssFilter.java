package com.cy.own.common.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class XssFilter implements Filter {

    //过滤白名单
    String[] includeUrls = new String[]{"/app/","/base/","/css/","/images/","/js/","/plugins/"};
    FilterConfig filterConfig = null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //设置字符集
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uri = httpRequest.getRequestURI();
        boolean needFilter = isNeedFilter(uri);
        if(!needFilter){
            chain.doFilter(httpRequest, httpResponse);
        }else{
            chain.doFilter(new XssHttpServletRequestWrapper(httpRequest),httpResponse);
        }
    }

    @Override
    public void destroy() {
        this.filterConfig=null;
    }

    /**
     * 是否需要过滤
     * @param uri
     * @return
     */
    public boolean isNeedFilter(String uri) {

        for (String includeUrl : includeUrls) {
            if(uri.contains(includeUrl)) {
                return false;
            }
        }

        return true;
    }
}
