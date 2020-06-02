package com.user.controller;//package自定义

import com.alibaba.fastjson.JSONObject;
import com.user.entity.User;
import com.user.service.UserService;
import com.user.utils.MessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.DigestUtils;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
* Author:https://github.com/TianYiHan
* Date:Mon May 25 10:42:41 CST 2020
* Project:ProjectName
* 表注释:
*/
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController{

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 登录
     * @author:https://github.com/TianYiHan
     * @date: Mon May 25 10:42:41 CST 2020
     * @return: int
     */   //where  `Mobile` = #{mobile} and `Password` = #{password}
    @PostMapping(value = "/login")
    public String login(@RequestParam Map params,HttpServletRequest request){

        String mobile = String.valueOf(params.get("mobile"));//获取 手机  密码（明文）
        String password = String.valueOf(params.get("password"));//获取 手机  密码（明文）
        String smscode = String.valueOf(params.get("smscode"));//获取验证码
        User user =null;
        if (mobile!=null&&mobile!=""&&mobile.matches("1[3456789]\\d{9}$")&&password!=null||smscode!=null){//手机和密码输入判空
            HashMap<String, String> map = new HashMap<>();
            map.put("mobile",mobile);
            user = userService.login(map);
            if (user!=null){
                if (smscode!=null&&smscode!=""&&smscode!="null"&&!smscode.equals("null")){//验证码登录
                        System.err.println(smscode);
                        System.err.println("验证码登录");
                        ValueOperations ops = stringRedisTemplate.opsForValue();//StringRedisTemplate 或者 RedisTemplate 工具类
                        String redisverificationcode = String.valueOf(ops.get(mobile));//去redis获取验证码
                        if (redisverificationcode!=null&&redisverificationcode!=""){
                            if (redisverificationcode.equals(smscode)){//验证码输入正确，允许登录
                                System.out.println("验证码输入正确，允许登录");
                                user.setActiveip(getIpAddress(request));//更新用户最后活跃ip
                                user.setActivetime(new Date());//更新用户活跃时间信息
                                user.setPwderrorcount(0);//更新密码错误次数为0
                                userService.modifyUser(user);
                                HashMap<String, Object> rtnmap = new HashMap<>();
                                rtnmap.put("status","ok");
                                rtnmap.put("msg","");
                                rtnmap.put("SMS","");
                                rtnmap.put("why","");
                                rtnmap.put("phone","");
                                rtnmap.put("user",user);
                                return JSONObject.toJSONString(rtnmap);
                            }else {//验证码输入错误
                                System.out.println("redis里获取到了 ,输入的不对");
                                System.out.println("redis里的是："+redisverificationcode);
                                HashMap<String, Object> rtnmap = new HashMap<>();
                                rtnmap.put("status","error");
                                rtnmap.put("msg","Wrong SMScode");
                                rtnmap.put("SMS","");
                                rtnmap.put("why","");
                                rtnmap.put("phone","");
                                rtnmap.put("user",user);
                                return JSONObject.toJSONString(rtnmap);
                            }
                        }else{
                            HashMap<String, Object> rtnmap = new HashMap<>();
                            rtnmap.put("status","error");
                            rtnmap.put("msg","Null SMScode");
                            rtnmap.put("SMS","");
                            rtnmap.put("why","");
                            rtnmap.put("phone","");
                            rtnmap.put("user",user);
                            return JSONObject.toJSONString(rtnmap);
                        }

                }else{//不要验证码验证 密码登录
                    System.err.println("不要验证码验证 密码登录");
                        //账户存在 需要判断
                    System.out.println((new Date().getTime()-user.getActivetime().getTime()));
                        if (2592000000L<(new Date().getTime()-user.getActivetime().getTime())){//上次活跃时间大于一个月了
                            System.out.println("上次活跃时间大于一个月了，需要短信验证");
                            HashMap<String, Object> yzmmap = new HashMap<>();//发送短信验证码
                            yzmmap.put("mobile",mobile);//发送短信验证码
                            yzmmap.put("why","longtime");//发送短信验证码
                            getverificationCode(yzmmap,request);//发送短信验证码
                            HashMap<String, Object> rtnmap = new HashMap<>();
                            rtnmap.put("status","error");
                            rtnmap.put("msg","longtime");
                            rtnmap.put("SMS","yes");
                            rtnmap.put("why","longtime");
                            rtnmap.put("phone",mobile.substring(7,11));
                            rtnmap.put("user","");
                            return JSONObject.toJSONString(rtnmap);//需要短信验证
                        }else if (user.getPwderrorcount()>4){//密码错误大于5次
                            System.out.println("密码错误大于5次，需要短信验证");
                            HashMap<String, Object> yzmmap = new HashMap<>();//发送短信验证码
                            yzmmap.put("mobile",mobile);//发送短信验证码
                            yzmmap.put("why","manyerror");//发送短信验证码
                            getverificationCode(yzmmap,request);//发送短信验证码
                            HashMap<String, Object> rtnmap = new HashMap<>();
                            rtnmap.put("status","error");
                            rtnmap.put("msg","manyerror");
                            rtnmap.put("SMS","yes");
                            rtnmap.put("why","manyerror");
                            rtnmap.put("phone",mobile.substring(7,11));
                            rtnmap.put("user","");
                            return JSONObject.toJSONString(rtnmap);//需要短信验证
                        }else if (!getIpAddress(request).equals(user.getActiveip())){//最后活跃ip和本次登录ip比较
                            System.out.println("最后活跃ip和本次登录ip不一致，需要短信验证");
                            HashMap<String, Object> yzmmap = new HashMap<>();//发送短信验证码
                            yzmmap.put("mobile",mobile);//发送短信验证码
                            yzmmap.put("why","newip");//发送短信验证码
                            getverificationCode(yzmmap,request);//发送短信验证码
                            HashMap<String, Object> rtnmap = new HashMap<>();
                            rtnmap.put("status","error");
                            rtnmap.put("msg","newip");
                            rtnmap.put("SMS","yes");
                            rtnmap.put("why","newip");
                            rtnmap.put("phone",mobile.substring(7,11));
                            rtnmap.put("user","");
                            return JSONObject.toJSONString(rtnmap);//需要短信验证
                        }else {
                            //对比密码
                            //盐=手机号码md5加密  盐存入数据库
                            String mobilemd5 = DigestUtils.md5DigestAsHex(mobile.getBytes());
                            //排列密码+盐  然后MD5加密 存入数据库
                            String passwordmd5 = DigestUtils.md5DigestAsHex((password+mobilemd5).getBytes());
                            if (!passwordmd5.equals(String.valueOf(user.getPassword()))){
                                user.setPwderrorcount(user.getPwderrorcount()+1);
                                userService.modifyUser(user);//密码错误次数+1

                                HashMap<String, Object> rtnmap = new HashMap<>();
                                rtnmap.put("status","error");
                                rtnmap.put("msg","passworderror");
                                rtnmap.put("SMS","");
                                rtnmap.put("why","");
                                rtnmap.put("phone","");
                                rtnmap.put("user","");

                                return JSONObject.toJSONString(rtnmap);
                            }else {//密码正确，更新活跃状态
                                user.setActiveip(getIpAddress(request));//更新用户最后活跃ip
                                user.setActivetime(new Date());//更新用户活跃时间信息
                                user.setPwderrorcount(0);//更新密码错误次数为0
                                userService.modifyUser(user);
                                HashMap<String, Object> rtnmap = new HashMap<>();
                                rtnmap.put("status","ok");
                                rtnmap.put("msg","");
                                rtnmap.put("SMS","");
                                rtnmap.put("why","");
                                rtnmap.put("phone","");
                                rtnmap.put("user",user);
                                return JSONObject.toJSONString(rtnmap);
                            }
                        }
                }
            }else {//user==null
                return JSONObject.toJSONString("Null Account");
            }
        }else{
            return JSONObject.toJSONString("Params Lack");
        }
    };


    /**
     * 获取验证码
     * @author:https://github.com/TianYiHan
     * @date: Mon May 25 10:42:41 CST 2020
     */
    @PostMapping(value = "/getverificationCode")
    public String getverificationCode(@RequestParam Map params, HttpServletRequest request){
        String mobile = String.valueOf(params.get("mobile"));//获取手机
        String why = String.valueOf(params.get("why"));//获取原因

        //why  = "Login"  "Registered" "RetrievePassword" 2020.06.01

        if (why!=null&&why!=""&&mobile!=null&&mobile!=""&&mobile.matches("1[3456789]\\d{9}$")){
            String yzm=String.valueOf(new Random().nextInt(999999 - 100000) + 100000);//生成6位随机数
            ValueOperations ops = stringRedisTemplate.opsForValue();//StringRedisTemplate 或者 RedisTemplate
            //发送短信
            //发送短信
            //发送短信
            ops.set(mobile+why,yzm,300L,TimeUnit.SECONDS);//设置有效期 300秒  k,v   手机号码，验证码，有效期
            return JSONObject.toJSONString("短信验证码已发送，有效期5分钟，请注意查收~");
        }else {
            System.out.println("参数有误！");
            return JSONObject.toJSONString("Wrong parameter");
        }

    };

    /**
     * registered注册
     * @author:https://github.com/TianYiHan
     * @date: Mon May 25 10:42:41 CST 2020
     * @return: int
     */
    @PostMapping(value = "/registered")
    public String registered(@RequestParam Map params,HttpServletRequest request){
        ValueOperations ops = stringRedisTemplate.opsForValue();//StringRedisTemplate 或者 RedisTemplate 工具类
        String mobile = String.valueOf(params.get("mobile"));//获取手机
        String password = String.valueOf(params.get("password"));//获取密码
        String verificationcode = String.valueOf(params.get("verificationcode"));//获取验证码
        if (mobile!=null&&mobile!=""&&password!=null&&password!=""&&verificationcode!=null&&verificationcode!=""){

            String redisverificationcode = String.valueOf(ops.get(mobile));//去redis获取验证码
            if (redisverificationcode!=null&&redisverificationcode!=""){
                if (!redisverificationcode.equals(verificationcode)){
                    //用户输入错误
                    System.out.println("redis里获取到了 ,输入的不对");
                    System.out.println("redis里的是："+redisverificationcode);
                    return JSONObject.toJSONString("Wrong smscode");
                }else {
                    //盐=手机号码md5加密  盐存入数据库
                    String mobilemd5 = DigestUtils.md5DigestAsHex(mobile.getBytes());
                    //排列密码+盐  然后MD5加密 存入数据库
                    String passwordmd5 = DigestUtils.md5DigestAsHex((password+mobilemd5).getBytes());
                    //密码= MD5(排列+MD5(盐))
                    //mobile    mobilemd5   passwordmd5
                    User user= new User();
                    user.setMobile(mobile);//手机号码
                    user.setMobilestatus("ok");//手机号码已经验证
                    user.setCreatetime(new Date());//注册时间
                    user.setActivetime(new Date());//最后活跃时间
                    user.setActiveip(getIpAddress(request));//最后活跃ip
                    user.setImgaddress("defautlimg");//默认头像
                    user.setPasswordsalt(mobilemd5);//盐
                    user.setPassword(passwordmd5);//MD5密码
                    user.setName("新用户"+mobile.substring(3,11));//用户名为手机号码
                    user.setLevel("1");//初始等级
                    user.setEmailstatus("no");//初始邮箱状态
                    user.setAddress("未知地区");//初始地区
                    user.setIntroduce("暂无简介");//初始简介
                    user.setMoney(0);//用户初始金额
                    if (userService.addUser(user) == 1) {
                        ops.set(mobile,"Used", 1L, TimeUnit.SECONDS);//设置有效期 1秒
                        return JSONObject.toJSONString("registered user success");
                    }else{
                        return JSONObject.toJSONString("registered user error");
                    }
                }
            }else{
                return JSONObject.toJSONString("null smscode");
            }
        }else {
            return JSONObject.toJSONString("Wrong parameter");//参数错误
        }
    };





    /**
     * 获取Ip地址
     * @param request
     * @return
     */
    private static String getIpAddress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }




}