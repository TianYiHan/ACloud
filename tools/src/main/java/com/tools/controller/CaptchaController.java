package com.tools.controller;
import com.tools.entity.CaptchaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Author:HanTianYi
 * Date:2020/4/15 14:26
 * Project:ACloud
 * package:com.tools.controller
 * about:获取验证码接口
 */
@RestController
@Slf4j
public class CaptchaController {

    @RequestMapping("getCaptcha")
    public void getCaptcha(HttpServletRequest req, HttpServletResponse resp)throws IOException {


        HttpSession session = req.getSession();
        String sessionId = session.getId();
        //获取redis连接
        Jedis jedis = new Jedis("106.14.208.219",6379);
        //查看服务是否运行

        System.out.println("服务正在运行: "+jedis.ping());
        jedis.set(sessionId,sessionId);
        jedis.expire(sessionId,10);//设置sessionId过期时间》秒
        System.out.println(jedis.get(sessionId));
        jedis.close();

        new CaptchaUtils().getCaptcha(req,resp);
        System.out.println(session.getAttribute("CaptchaCode"));
    }




}
