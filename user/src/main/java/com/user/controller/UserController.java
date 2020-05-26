package com.user.controller;//package自定义

import com.user.entity.User;
import com.user.service.UserService;
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
public class UserController {

    @Autowired
    private UserService userService;

    //where `Email` = #{email} or `Mobile` = #{mobile} and `Password` = #{password}
    /**
     * 登录
     * @author:https://github.com/TianYiHan
     * @date: Mon May 25 10:42:41 CST 2020
     * @return: int
     */
    @PostMapping(value = "/selectUserByParams")
    public String login(@RequestBody Map params){
        //获取用户名和密码（明文）
        //倒序排列用户名和密码 然后md5加密
        //对比数据库数据是否正确

        return "json List<User>";
    };

    /**
    * 添加
    * @author:https://github.com/TianYiHan
    * @date: Mon May 25 10:42:41 CST 2020
    * @return: int
    */
    @PostMapping(value = "/insertUser")
    public String insertUser(@RequestBody User bean){
        //获取用户名和密码
        //do

        return "true";
    };


    /**
    * 删除User
    * @param bean
    * @return
    */
    @DeleteMapping(value = "/deleteUser")
    public String deleteUser(@RequestBody User bean){

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