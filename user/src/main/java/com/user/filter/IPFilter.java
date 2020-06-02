package com.user.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Author:HanTianYi
 * Date:2020/6/2 16:47
 * Project:ACloud
 * package:com.user.filter
 *
 * 过滤器讲解
 * https://blog.csdn.net/yuzhiqiang_1993/article/details/81288912
 */
@WebFilter(urlPatterns = "/*")
public class IPFilter  implements Filter {
    private String filterName;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filterName= filterConfig.getFilterName();
        System.out.println("过滤器名称：" + filterName +" init,此过滤器过滤短时间内重复访问的ip");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
