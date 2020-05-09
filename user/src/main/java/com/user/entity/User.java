package com.user.entity;

import lombok.Data;
import lombok.Value;

/**
 * Author:HanTianYi
 * Date:2020/4/15 11:24
 * Project:ACloud
 * package:com.user.entity
 */
public class User {

    int id;

    String userName;

    String nickName;

    String realName;

    String idEntity;

//    一般会在表中增加salt，也就是加点盐，和真实密码进行某种聚合。也就是：
//    数据库中的密码=随机密码+用户输入的密码，进行某种算法聚合的
    //https://blog.csdn.net/qq78442761/article/details/104605661
    String salt;



}
