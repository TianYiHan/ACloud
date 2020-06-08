package com.user.utils;

import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Author:HanTianYi
 * Date:2020/6/4 17:30
 * Project:ACloud
 * package:com.user.utils
 * JWT Token工具类
 */
public class TokenUtil {


    //生成token
    //map  头信息
    //str  jsonString
    //str  密码
    public static String createJWT(Map<String, Object> header,String payload ,String signature){
        return Jwts.builder().setHeader(header).setPayload(payload)
                .signWith(SignatureAlgorithm.HS512, new SecretKeySpec(signature.getBytes(),SignatureAlgorithm.HS512.getJcaName()))
                .compact();
    }


    //jwt验证 ，是否准确
    public static boolean parserJTW(HttpServletRequest httpRequest,String JWT,String signature){
        Jws<Claims> claimsJws = Jwts.parser().//解析JWT
                setSigningKey(new SecretKeySpec(signature.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .parseClaimsJws(JWT);
        if(MessageUtil.getIpAddress(httpRequest)!=claimsJws.getHeader().get("CreatTonekIP")){//当前访问ip和token里的ip不一致 拦截
            System.out.println("当前访问ip和token里的ip不一致 拦截 false");
            return  false;
        }else if(new Date().getTime()-Integer.valueOf((Integer)claimsJws.getHeader().get("creatTime"))>Integer.valueOf((Integer)claimsJws.getHeader().get("Time"))){
            //token里的最大有效期过了 拦截
            System.out.println("token过了最大有效期");
            return  false;
        }else if(claimsJws.getHeader().get("usermobile")!=signature){
            //token里的用户手机 和访问带的手机不一致
            System.out.println("token里的手机号码和Request Header的参数不一致 ");
            return  false;
        }return  true;
    }
}
