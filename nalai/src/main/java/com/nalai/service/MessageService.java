package com.nalai.service;

import com.nalai.entity.Message;

import java.util.List;
import java.util.Map;

/**
* Author:https://github.com/TianYiHan
* Date:Fri May 22 09:15:27 CST 2020
* 表注释:
*/
public interface MessageService {

    /**
    * 插入Message(非空)
    * @param bean
    * @return
    */
    public int addMessage(Message bean);

    /**
    * 删除Message
    *
    * @param bean
    * @return
    */
    public int  removeMessage(Message bean);

    /**
    * 修改Message
    *
    * @param bean
    * @return
    */
    public int  modifyMessage(Message bean);

    /**
    * 查询Message
    *
    * @param params
    * @return
    */
    public List<Message> queryMessageByParams(Map params);




}