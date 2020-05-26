package com.nalai.service.impl;

import com.nalai.entity.Codeinfo;
import com.nalai.mapper.CodeinfoMapper;
import com.nalai.service.CodeinfoService;
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
public class CodeinfoServiceImpl implements CodeinfoService {

    @Autowired
    private CodeinfoMapper mapper;

    /**
    * 新增Codeinfo
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int addCodeinfo(Codeinfo bean){
        int res=0;
 		try {
 			if(bean != null){
                res=mapper.insertCodeinfo(bean);
 			}
 		} catch (Exception e) {
             //手动事务回滚
             TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
 	    }
 		return res;
    }

    /**
    * 删除Codeinfo
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int removeCodeinfo(Codeinfo bean){
        int res=0;
        try {
            if(bean != null){
                res=mapper.deleteCodeinfo(bean);
            }
        } catch (Exception e) {
        //手动事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return res;
    }


    /**
    * 修改Codeinfo
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int modifyCodeinfo(Codeinfo bean){
        int res=0;
        try {
            if(bean != null){
                res=mapper.updateCodeinfo(bean);
            }
        } catch (Exception e) {
            //手动事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return res;
    }


    /**
    * 根据Map里的参数，查询Codeinfo集合
    */
    @Override
    public List<Codeinfo> queryCodeinfoByParams(Map params){
        List<Codeinfo> list = null;
        try {
            if(params != null){
                 list=mapper.selectCodeinfoByParams(params);
            }
        } catch (Exception e) {
            //error
            System.err.println(e.getMessage());
        }
        return list;
    }

}