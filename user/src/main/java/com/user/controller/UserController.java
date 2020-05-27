package com.user.controller;//package自定义

import com.user.entity.User;
import com.user.service.UserService;
import com.user.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
* Author:https://github.com/TianYiHan
* Date:Mon May 25 10:42:41 CST 2020
* Project:ProjectName
* 表注释:
*/
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 登录
     * @author:https://github.com/TianYiHan
     * @date: Mon May 25 10:42:41 CST 2020
     * @return: int
     */   //where  `Mobile` = #{mobile} and `Password` = #{password}
    @PostMapping(value = "/login")
    public String login(@RequestParam Map params){
        String mobile = String.valueOf(params.get("mobile"));
        String password = String.valueOf(params.get("password"));
        String code = MessageUtils.sendcode(String.valueOf(params.get("mobile")));


        //获取 手机  密码（明文）
        //md5(倒序排列密码然后+MD5加密的手机号码)
        //对比数据库数据的手机，密码是否正确
        return "true";
    };


    /**
     * registered注册
     * @author:https://github.com/TianYiHan
     * @date: Mon May 25 10:42:41 CST 2020
     * @return: int
     */
    @PostMapping(value = "/registered")
    public String registered(@RequestBody Map params){
        System.out.println(params);
        //获取手机号和密码
        //发送短信验证码
        //验证验证码是否正确
        //盐=手机号码md5加密  盐存入数据库
        //正确的话倒序排列密码+盐  然后MD5加密 存入数据库
        //密码= MD5(倒序排列+MD5(盐))
        //存入明文手机号码    加密的盐  加密的密码
        return "true";
    };

    /**
    * 添加
    * @author:https://github.com/TianYiHan
    * @date: Mon May 25 10:42:41 CST 2020
    * @return: int
    */
    @PostMapping(value = "/insertUser")
    public String insertUser(@RequestBody  Map params){

        //获取手机号和密码
        //发送短信验证码
        //验证验证码是否正确
        //盐=手机号码md5加密  盐存入数据库
        //正确的话倒序排列密码+盐  然后MD5加密 存入数据库
        //密码= MD5(倒序排列+MD5(盐))
        //存入明文手机号码    加密的盐  加密的密码
        return "true";
    };


    /**
    * 删除User
    * @param  params
    * @return
    */
    @DeleteMapping(value = "/deleteUser")
    public String deleteUser(@RequestBody Map params){

        //do

        return "true";

    };

    /**
    * 修改信息
    * @param bean
    * @return
    */
    @PutMapping(value = "/updateUser")
    public String updateUser(@RequestBody User bean){

        //do

        return "true";

    };

    /**
    * 查询信息
    * @param params
    * @return
    *get请求，参数不能是json(application/json;charset=utf-8)格式，只能是表单(application/x-www-form-urlencoded)格式
    */
    @PostMapping(value = "/selectUserByParams")
    public String selectUserByParams(@RequestBody Map params){
         //

        return "json List<User>";
    };






}