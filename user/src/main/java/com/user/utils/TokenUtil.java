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


/**
 * Author:HanTianYi
 * Date:2020/6/4 17:30
 * Project:ACloud
 * package:com.user.utils
 * JWT Token工具类
 */
public class TokenUtil {



    public static String createJWT(Map<String, Object> header,String payload ,String signature){
        return Jwts.builder().setHeader(header).setPayload(payload)
                .signWith(SignatureAlgorithm.HS512, new SecretKeySpec(signature.getBytes(),SignatureAlgorithm.HS512.getJcaName()))
                .compact();
    }

    public static Map<String, Object> parserJTW(String JWT,String signature){
        Map<String, Object> res=new HashMap<>();
        Jws<Claims> claimsJws = Jwts.parser().
                setSigningKey(new SecretKeySpec(signature.getBytes(),SignatureAlgorithm.HS512.getJcaName()))
                .parseClaimsJws(JWT);
        res.put("header",claimsJws.getHeader());//header CreatTonekIP
        res.put("payload",claimsJws.getBody());
        res.put("signature",claimsJws.getSignature());
        return res;
    }


}
