package com.tools.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

/**
 * Author:HanTianYi
 * Date:2020/4/16 10:55
 * Project:ACloud
 * package:com.tools.entity
 */


@Slf4j
public class RedisUtils {

    private static Properties p;
    private static volatile JedisPool jedisPool;
    //双重检查锁的单例模式
    public static JedisPool getJedisPool() {
        if (null==jedisPool){
            synchronized (RedisUtils.class){
                if (null==jedisPool){
                    p = new Properties();
                    InputStream stream = RedisUtils.class.getClassLoader().getResourceAsStream("application.properties");
                    try {
                        p.load(stream);
                    } catch (IOException e) {
                        log.info("读取文件异常");
                        //validTimeInterceptor
                        e.printStackTrace();
                    }
                    String redishost=p.getProperty("spring.redis.host")==null?null:p.getProperty("spring.redis.host");
                    int redisport=Integer.valueOf(p.getProperty("spring.redis.port"));
                    jedisPool =new JedisPool(redishost,redisport);
                    log.info("new Redis连接池JedisPool======host:"+redishost+" port:"+redisport+"===============================");
                    return jedisPool;
                }
            }
        }
        return jedisPool;
    }


    public static Jedis getJedis(){
        getJedisPool();//first
        return  jedisPool.getResource();
    }










}
