package com.nalai.service.impl;

import com.nalai.entity.Message;
import com.nalai.mapper.MessageMapper;
import com.nalai.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import java.util.List;
import java.util.Map;
/**
* Author:https://github.com/TianYiHan
* Date:Fri May 22 09:15:27 CST 2020
* 表注释:
*/
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper mapper;

    /**
    * 新增Message
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int addMessage(Message bean){
        int res=0;
 		try {
 			if(bean != null){
                res=mapper.insertMessage(bean);
 			}
 		} catch (Exception e) {
             //手动事务回滚
             TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
 	    }
 		return res;
    }

    /**
    * 删除Message
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int removeMessage(Message bean){
        int res=0;
        try {
            if(bean != null){
                res=mapper.deleteMessage(bean);
            }
        } catch (Exception e) {
        //手动事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return res;
    }


    /**
    * 修改Message
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int modifyMessage(Message bean){
        int res=0;
        try {
            if(bean != null){
                res=mapper.updateMessage(bean);
            }
        } catch (Exception e) {
            //手动事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return res;
    }


    /**
    * 根据Map里的参数，查询Message集合
    */
    @Override
    public List<Message> queryMessageByParams(Map params){
        List<Message> list = null;
        try {
            if(params != null){
                 list=mapper.selectMessageByParams(params);
            }
        } catch (Exception e) {
            //error
            System.err.println(e.getMessage());
        }
        return list;
    }

}