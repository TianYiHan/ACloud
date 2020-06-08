package com.user.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import lombok.Synchronized;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
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

@WebFilter(urlPatterns = "/*", filterName = "tokenfilter")
public class TokenFilter implements Filter {
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/user/login","/user/getverificationCode","/user/registered")));

    private String filterName;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filterName= filterConfig.getFilterName();
        System.out.println("过滤器名称：" + filterName +" init,此过滤器过滤token失效的用户");
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
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length()).replaceAll("[/]+$", "");
        boolean allowedPath = ALLOWED_PATHS.contains(path);
        System.out.println("当前path:"+path);
        String JWT=httpRequest.getHeader("JWT");
        String moblie=httpRequest.getHeader("moblie");
        if (JWT==null||moblie==null){
            System.out.println("Request Header 缺少参数");
            return;
        }




    }

    @Override
    public void destroy() {

    }


}
