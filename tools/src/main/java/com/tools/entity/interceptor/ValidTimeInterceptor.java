package com.tools.entity.interceptor;


import com.tools.entity.CaptchaUtils;
import com.tools.entity.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Author:HanTianYi
 * Date:2020/4/17 11:16
 * Project:ACloud
 * package:com.tools.entity.interceptor
 * ValidTime拦截器有效时间
 * 主要是用来拦截短时间内同一用户重复访问同一接口的拦截器
 */
@Slf4j
public class ValidTimeInterceptor implements HandlerInterceptor {

    /**
     * 预处理回调方法，实现处理器的预处理
     * 返回值：true表示继续流程；false表示流程中断，不会继续调用其他的拦截器或处理器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        synchronized (ValidTimeInterceptor.class){
            log.info("======进入拦截器========================");
            HttpSession session = request.getSession();
            String sessionId = session.getId();
            Jedis jedis = RedisUtils.getJedis();

            if (null==jedis.get("redis"+sessionId)){
                jedis.set("redis"+sessionId,"redis"+sessionId);
                jedis.expire("redis"+sessionId,1);//设置sessionId过期时间》秒
                jedis.close();
                return true;
            }

            jedis.close();
            return false;
        }

    }

    /**
     * 后处理回调方法，实现处理器（controller）的后处理，但在渲染视图之前
     * 此时我们可以通过modelAndView对模型数据进行处理或对视图进行处理
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }
    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，
     * 如性能监控中我们可以在此记录结束时间并输出消耗时间，
     * 还可以进行一些资源清理，类似于try-catch-finally中的finally，
     * 但仅调用处理器执行链中
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        //AdminConfigInterceptor

    }



}
