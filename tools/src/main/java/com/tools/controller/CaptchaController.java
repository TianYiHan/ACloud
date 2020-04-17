package com.tools.controller;
import com.tools.entity.CaptchaUtils;
import com.tools.entity.RedisUtils;
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
        new CaptchaUtils().getCaptcha(req,resp);
        System.out.println(req.getSession().getAttribute("CaptchaCode"));
    }




}
