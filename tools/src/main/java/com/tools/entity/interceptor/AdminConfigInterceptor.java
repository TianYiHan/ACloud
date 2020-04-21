package com.tools.entity.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import com.tools.entity.interceptor.ValidTimeInterceptor;
/**
 * Author:HanTianYi
 * Date:2020/4/17 11:28
 * Project:ACloud
 * package:com.tools.entity.interceptor
 * 此类管理拦截器,每新增一个拦截器 需要在此类注册
 */
@Configuration
public class AdminConfigInterceptor extends WebMvcConfigurationSupport {


    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则，/**表示拦截所有请求
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new ValidTimeInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns();
        super.addInterceptors(registry);
    }

}
