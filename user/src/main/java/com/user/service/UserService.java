package com.user.service;//package自定义

import com.user.entity.User;

import java.util.List;
import java.util.Map;

/**
* Author:https://github.com/TianYiHan
* Date:Mon May 25 10:42:41 CST 2020
* 表注释:
*/
public interface UserService {

    /**
    * 插入User(非空)
    * @param bean
    * @return
    */
    public int addUser(User bean);

    /**
    * 删除User
    *
    * @param bean
    * @return
    */
    public int  removeUser(User bean);

    /**
    * 修改User
    *
    * @param bean
    * @return
    */
    public int  modifyUser(User bean);

    /**
    * 查询User
    *
    * @param params
    * @return
    */
    public List<User> queryUserByParams(Map params);


    public User login(Map params);



}