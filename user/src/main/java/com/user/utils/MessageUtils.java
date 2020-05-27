package com.user.utils;
import java.util.Random;

/**
 * Author:HanTianYi
 * Date:2020/5/26 11:35
 * Project:ACloud
 * package:com.user.utils
 * 发送短信工具类
 *
 */
public class MessageUtils {
    private String SecretId="AKIDd7PVbaxV6LQGtHKWt0ta2FDnAIIDquXw";
    private String SecretKey="LQCl6D5kALTO5btwnBKDya9au55wjCSZ";





    public static String sendcode(String mobile){
        String yzm=String.valueOf(new Random().nextInt(999999 - 100000) + 100000);



        System.out.println("给"+mobile+"发送了短信，验证码为："+yzm);



        return  yzm;
    }



}
