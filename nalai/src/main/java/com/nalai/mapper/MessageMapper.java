package com.nalai.mapper;

import com.nalai.entity.Message;

import java.util.List;
import java.util.Map;


/**
* Author:https://github.com/TianYiHan
* Date:Fri May 22 09:15:27 CST 2020
* 表注释:
*/
public interface MessageMapper {

    /**
    * 插入Message(非空)
    * @param bean
    * @return
    */
    public int insertMessage(Message bean);

    /**
    * 删除Message
    *
    * @param bean
    * @return
    */
    public int deleteMessage(Message bean);

    /**
    * 修改信息
    *
    * @param bean
    * @return
    */
    public int updateMessage(Message bean);

    /**
    * 查询信息
    *
    * @param params
    * @return
    */
    public List<Message> selectMessageByParams(Map params);



}