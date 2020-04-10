package com.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy//zuul网关
@EnableEurekaClient
public class GatewayApplication {

    public static void main(String[] args) {
        System.out.println("======<网关启动>======");
        SpringApplication.run(GatewayApplication.class, args);
    }

}
