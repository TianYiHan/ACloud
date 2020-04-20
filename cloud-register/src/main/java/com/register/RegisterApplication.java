package com.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegisterApplication  {

    public static void main(String[] args) {
        SpringApplication.run(RegisterApplication.class, args);
    }

//    /***
//     * 重写WebSecurityConfigurerAdapter的configure方法，
//     * 因为 Spring Security 默认开启了所有 CSRF 攻击防御，
//     * 需要禁用 /eureka 的防御。
//     * @param http
//     * @throws Exception
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().ignoringAntMatchers("/eureka/**");
//        super.configure(http);
//    }

}