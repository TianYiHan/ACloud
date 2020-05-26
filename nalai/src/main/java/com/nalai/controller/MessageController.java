package com.nalai.controller;

import com.nalai.entity.Message;
import com.nalai.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
* Author:https://github.com/TianYiHan
* Date:Fri May 22 09:15:27 CST 2020
* Project:ProjectName
* 表注释:
*/
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;


    /**
    * 添加
    * @author:https://github.com/TianYiHan
    * @date: Fri May 22 09:15:27 CST 2020
    * @return: int
    */
    @PostMapping(value = "/insertMessage")
    public String insertMessage(@RequestBody Message bean){

        //do

        return "true";
    };


    /**
    * 删除Message
    * @param bean
    * @return
    */
    @DeleteMapping(value = "/deleteMessage")
    public String deleteMessage(@RequestBody Message bean){

        //do

        return "true";

    };

    /**
    * 修改信息
    * @param bean
    * @return
    */
    @PutMapping(value = "/updateMessage")
    public String updateMessage(@RequestBody Message bean){

        //do

        return "true";

    };

    /**
    * 查询信息
    * @param params
    * @return
    *get请求，参数不能是json(application/json;charset=utf-8)格式，只能是表单(application/x-www-form-urlencoded)格式
    */
    @PostMapping(value = "/selectMessageByParams")
    public String selectMessageByParams(@RequestBody Map params){

        //

        return "json List<Message>";
    };




}