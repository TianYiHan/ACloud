package com.user.entity;

import lombok.Data;

/**
 * Author:HanTianYi
 * Date:2020/4/21 09:39
 * Project:ACloud
 * package:com.user.entity
 */
@Data
public class Comment {

    private int id;
    private int blogid;
    private String commentman;
    private String commentcontant;
    private String huifu;
    private int zan;


}
