package com.user.controller;//package自定义

import com.alibaba.fastjson.JSONObject;
import com.user.entity.User;
import com.user.service.UserService;
import com.user.utils.MessageUtil;
import com.user.utils.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.DigestUtils;
import javax.servlet.http.HttpServletRequest;
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
        ValueOperations ops = stringRedisTemplate.opsForValue();//StringRedisTemplate 或者 RedisTemplate 工具类
        String mobile = String.valueOf(params.get("mobile"));//获取 手机  密码（明文）
        String password = String.valueOf(params.get("password"));//获取 手机  密码（明文）
        User user =null;
        if (mobile!=null&&mobile!=""&&mobile.matches("1[3456789]\\d{9}$")&&password!=null){//手机和密码输入判空
            HashMap<String, String> map = new HashMap<>();
            map.put("mobile",mobile);
            user = userService.login(map);
            if (user!=null){
                if (password.length()==6){//验证码登录
                        String redisverificationcode = String.valueOf(ops.get(mobile));//去redis获取验证码
                        if (redisverificationcode!=null&&redisverificationcode!=""){
                            if (redisverificationcode.equals(password)){//验证码输入正确，允许登录
                                user.setActiveip(MessageUtil.getIpAddress(request));//更新用户最后活跃ip
                                user.setActivetime(new Date());//更新用户活跃时间信息
                                user.setPwderrorcount(0);//更新密码错误次数为0
                                userService.modifyUser(user);
                                HashMap<String, Object> rtnmap = new HashMap<>();
                                HashMap<String, Object> tonekmap = new HashMap<>();
                                tonekmap.put("CreatTonekIP",MessageUtil.getIpAddress(request));
                                tonekmap.put("usermobile",mobile);
                                tonekmap.put("creatTime",new Date().getTime());
                                tonekmap.put("Time",86400000L);//最大有效期
                                String token=TokenUtil.createJWT(tonekmap,JSONObject.toJSONString(user),user.getMobile());
                                rtnmap.put("status","ok");
                                rtnmap.put("msg","ok");
                                rtnmap.put("SMS","");
                                rtnmap.put("why","");
                                rtnmap.put("phone","");
                                rtnmap.put("user",user);
                                ops.set(token,token, 600L, TimeUnit.SECONDS);//设置有效期 10分钟，10分钟内操作没问题
                                rtnmap.put("usertoken",token);
                                ops.set(mobile,"Used", 1L, TimeUnit.SECONDS);//设置有效期 1秒
                                return JSONObject.toJSONString(rtnmap);
                            }else {//验证码输入错误
                                HashMap<String, Object> rtnmap = new HashMap<>();
                                rtnmap.put("status","error");
                                rtnmap.put("msg","SMScodeWrong");
                                rtnmap.put("SMS","");
                                rtnmap.put("why","");
                                rtnmap.put("phone","");
                                rtnmap.put("user","");
                                return JSONObject.toJSONString(rtnmap);
                            }
                        }else{
                            HashMap<String, Object> rtnmap = new HashMap<>();
                            rtnmap.put("status","error");
                            rtnmap.put("msg","NullSMScode");
                            rtnmap.put("SMS","");
                            rtnmap.put("why","");
                            rtnmap.put("phone","");
                            rtnmap.put("user","");
                            return JSONObject.toJSONString(rtnmap);
                        }

                }else{//不要验证码验证 密码登录
                        //账户存在 需要判断
                        if (2592000000L<(new Date().getTime()-user.getActivetime().getTime())){//上次活跃时间大于一个月了
                            getverificationCode(mobile,request);//发送短信验证码
                            HashMap<String, Object> rtnmap = new HashMap<>();
                            rtnmap.put("status","error");
                            rtnmap.put("msg","longtime");
                            rtnmap.put("SMS","yes");
                            rtnmap.put("why","长时间未登录");
                            rtnmap.put("phone",mobile.substring(7,11));
                            rtnmap.put("user","");
                            return JSONObject.toJSONString(rtnmap);//需要短信验证
                        }else if (!MessageUtil.getIpAddress(request).equals(user.getActiveip())){//最后活跃ip和本次登录ip比较
                            getverificationCode(mobile,request);//发送短信验证码
                            HashMap<String, Object> rtnmap = new HashMap<>();
                            rtnmap.put("status","error");
                            rtnmap.put("msg","newip");
                            rtnmap.put("SMS","yes");
                            rtnmap.put("why","和上次访问IP不一致");
                            rtnmap.put("phone",mobile.substring(7,11));
                            rtnmap.put("user","");
                            return JSONObject.toJSONString(rtnmap);//需要短信验证
                        }else if (user.getPwderrorcount()>4){//密码错误大于5次
                            getverificationCode(mobile,request);//发送短信验证码
                            HashMap<String, Object> rtnmap = new HashMap<>();
                            rtnmap.put("status","error");
                            rtnmap.put("msg","manyerror");
                            rtnmap.put("SMS","yes");
                            rtnmap.put("why","密码累计错误次数过多");
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
                                rtnmap.put("why","密码输入错误");
                                rtnmap.put("phone","");
                                rtnmap.put("user","");
                                return JSONObject.toJSONString(rtnmap);
                            }else {//密码正确，更新活跃状态
                                user.setActiveip(MessageUtil.getIpAddress(request));//更新用户最后活跃ip
                                user.setActivetime(new Date());//更新用户活跃时间信息
                                user.setPwderrorcount(0);//更新密码错误次数为0
                                userService.modifyUser(user);
                                HashMap<String, Object> rtnmap = new HashMap<>();
                                HashMap<String, Object> tonekmap = new HashMap<>();
                                tonekmap.put("CreatTonekIP",MessageUtil.getIpAddress(request));
                                tonekmap.put("usermobile",mobile);
                                tonekmap.put("creatTime",new Date().getTime());
                                tonekmap.put("Time",86400000L);
                                String token=TokenUtil.createJWT(tonekmap,JSONObject.toJSONString(user),user.getMobile());
                                rtnmap.put("status","ok");
                                rtnmap.put("msg","ok");
                                rtnmap.put("SMS","");
                                rtnmap.put("why","");
                                rtnmap.put("phone","");
                                rtnmap.put("user",user);
                                rtnmap.put("usertoken",token);
                                ops.set(token,token, 600L, TimeUnit.SECONDS);//设置有效期 10分钟，10分钟内操作没问题
                                ops.set(mobile,"Used", 1L, TimeUnit.SECONDS);//设置有效期 1秒
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
            if (password.length()<8){
                return JSONObject.toJSONString("PasswordLengthTooLow");
            }
            String redisverificationcode = String.valueOf(ops.get(mobile));//去redis获取验证码
            if (redisverificationcode!=null&&redisverificationcode!=""){
                if (!redisverificationcode.equals(verificationcode)){
                    //用户输入错误
                    return JSONObject.toJSONString("SMSERROR");
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
                    user.setActiveip(MessageUtil.getIpAddress(request));//最后活跃ip
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
                        user.setActiveip(MessageUtil.getIpAddress(request));//更新用户最后活跃ip
                        user.setActivetime(new Date());//更新用户活跃时间信息
                        user.setPwderrorcount(0);//更新密码错误次数为0
                        userService.modifyUser(user);
                        HashMap<String, Object> rtnmap = new HashMap<>();
                        HashMap<String, Object> tonekmap = new HashMap<>();//授权给mobile用户在xxip上免登录 10分钟
                        tonekmap.put("CreatTonekIP",MessageUtil.getIpAddress(request));
                        tonekmap.put("usermobile",mobile);
                        tonekmap.put("creatTime",new Date().getTime());
                        tonekmap.put("Time",86400000L);
                        String token=TokenUtil.createJWT(tonekmap,JSONObject.toJSONString(user),user.getMobile());
                        //用户登陆后生成token https://yq.aliyun.com/articles/594217
                        //getToken(rtnmap,request);
c
                        rtnmap.put("status","ok");
                        rtnmap.put("msg","registeredok");
                        rtnmap.put("SMS","");
                        rtnmap.put("why","");
                        rtnmap.put("phone","");
                        rtnmap.put("user",user);
                        rtnmap.put("usertoken",token);
                        ops.set(token,token, 600L, TimeUnit.SECONDS);//设置有效期 10分钟，10分钟内操作没问题
                        ops.set(mobile,"Used", 1L, TimeUnit.SECONDS);//设置有效期 1秒
                        return JSONObject.toJSONString(rtnmap);
                    }else{
                        return JSONObject.toJSONString("registeredERROR");
                    }
                }
            }else{
                return JSONObject.toJSONString("NULLsmscode");
            }
        }else {
            return JSONObject.toJSONString("WrongParameter");//参数错误
        }
    };


    /**
     * 获取验证码
     * @author:https://github.com/TianYiHan
     * @date: Mon May 25 10:42:41 CST 2020
     */
    @PostMapping(value = "/getverificationCode")
    public String getverificationCode(String mobile, HttpServletRequest request){


        if (mobile!=null&&mobile!=""&&mobile.matches("1[3456789]\\d{9}$")){
            String yzm=String.valueOf(new Random().nextInt(999999 - 100000) + 100000);//生成6位随机数
            ValueOperations ops = stringRedisTemplate.opsForValue();//StringRedisTemplate 或者 RedisTemplate
            //发送短信
            //发送短信
            System.out.println("手机："+mobile+"，验证码："+yzm);
            //发送短信
            ops.set(mobile,yzm,180L,TimeUnit.SECONDS);//设置有效期 300秒  k,v   手机号码，验证码，有效期
            return JSONObject.toJSONString("短信验证码已发送，有效期5分钟，请注意查收~");
        }else {
            System.out.println("参数有误！");
            return JSONObject.toJSONString("Wrong parameter");
        }

    };




}