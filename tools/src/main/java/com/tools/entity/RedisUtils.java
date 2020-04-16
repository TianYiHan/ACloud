package com.tools.entity;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

/**
 * Author:HanTianYi
 * Date:2020/4/16 10:55
 * Project:ACloud
 * package:com.tools.entity
 */
public class RedisUtils {


    public Jedis connectionRedis(String host,int port){
        //连接本地的 Redis 服务
        try {
            Jedis jedis = new Jedis(host,port);
            System.out.println("连接redis成功>"+host+":"+port);
            return jedis;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("连接redis失败！>"+host+":"+port);
            return null;
        }
    }








}
