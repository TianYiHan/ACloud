package com.tools.controller;
import com.tools.entity.CaptchaUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class CaptchaController {

    @RequestMapping("getCaptcha")
    public void getCaptcha(HttpServletRequest req, HttpServletResponse resp)throws IOException {
        new CaptchaUtils().getCaptcha(req,resp);
        HttpSession session = req.getSession();
        String a = session.getId();
        System.out.println(session.getAttribute("CaptchaCode"));
    }




}
