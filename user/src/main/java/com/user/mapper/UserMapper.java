package com.user.mapper;//package自定义

import com.user.entity.User;

import java.util.List;
import java.util.Map;


/**
* Author:https://github.com/TianYiHan
* Date:Mon May 25 10:42:41 CST 2020
* 表注释:
*/
public interface UserMapper {

    /**
    * 插入User(非空)
    * @param bean
    * @return
    */
    public int insertUser(User bean);

    /**
    * 删除User
    *
    * @param bean
    * @return
    */
    public int deleteUser(User bean);

    /**
    * 修改信息
    *
    * @param bean
    * @return
    */
    public int updateUser(User bean);

    /**
    * 查询信息
    *
    * @param params
    * @return
    */
    public List<User> selectUserByParams(Map params);

    /**
     * 用户登录
     * @param params
     * @return
     */
    public User login(Map params);


}