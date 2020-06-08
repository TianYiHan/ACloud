package com.user.filter;

import com.user.utils.MessageUtil;
import lombok.SneakyThrows;
import lombok.Synchronized;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Author:HanTianYi
 * Date:2020/6/2 16:47
 * Project:ACloud
 * package:com.user.filter
 *ip过滤 同一ip 最高每2秒一次访问
 * 过滤器讲解
 * https://blog.csdn.net/yuzhiqiang_1993/article/details/81288912
 */

@WebFilter(urlPatterns = "/*", filterName = "ipfilter")
public class IPFilter  implements Filter {
    private String filterName;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filterName= filterConfig.getFilterName();
        System.out.println("过滤器名称：" + filterName +" init,此过滤器过滤短时间内重复访问");
    }

    @SneakyThrows
    @Override
    @Synchronized
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;//跨域问题解决
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        ValueOperations ops = stringRedisTemplate.opsForValue();//StringRedisTemplate 或者 RedisTemplate 工具类

        if (ops.get(MessageUtil.getIpAddress(httpRequest))==null){
            ops.set(MessageUtil.getIpAddress(httpRequest),"this key is IpAddress",2L,TimeUnit.SECONDS);//设置有效期 2秒  k,v
            filterChain.doFilter(httpRequest, response);
        }else{
            return;
        }



    }

    @Override
    public void destroy() {

    }

}
