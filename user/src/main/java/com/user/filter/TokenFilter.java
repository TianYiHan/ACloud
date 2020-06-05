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
        Jws<Claims> claimsJws = Jwts.parser().//解析JWT
                setSigningKey(new SecretKeySpec(moblie.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .parseClaimsJws(JWT);
        if(allowedPath){//白名单,不需要token验证 直接放行
            filterChain.doFilter(httpRequest, response);
        }else if(getIpAddress(httpRequest)!=claimsJws.getHeader().get("CreatTonekIP")){//当前访问ip和token里的ip不一致 拦截
                System.out.println("当前访问ip和token里的ip不一致 拦截 false");
        }else if(new Date().getTime()-Integer.valueOf((Integer)claimsJws.getHeader().get("creatTime"))>Integer.valueOf((Integer)claimsJws.getHeader().get("Time"))){
            //token里的最大有效期过了 拦截
            System.out.println("token过了最大有效期");
        }else if(claimsJws.getHeader().get("usermobile")!=moblie){
            //token里的用户手机 和访问带的手机不一致
            System.out.println("token里的手机号码和Request Header的参数不一致 ");
        } else{
            if (ops.get(JWT)!=null){//token没毛病 再续费10分钟
                ops.set(JWT,JWT, 600L, TimeUnit.SECONDS);//续10分钟 10分钟不操作会被注销
                filterChain.doFilter(httpRequest, response);
            }else {
                System.out.println("tonek不存在");
            }
        }



    }

    @Override
    public void destroy() {

    }
    /**
     * 获取Ip地址
     * @param request
     * @return
     */
    private static String getIpAddress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }


}
