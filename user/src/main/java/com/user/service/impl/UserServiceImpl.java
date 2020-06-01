package com.user.service.impl;//package自定义


import com.user.entity.User;
import com.user.dao.UserMapper;
import com.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import java.util.List;
import java.util.Map;
/**
* Author:https://github.com/TianYiHan
* Date:Mon May 25 10:42:41 CST 2020
* 表注释:
*/
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    /**
    * 新增User
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int addUser(User bean){
        int res=0;
 		try {
 			if(bean != null){
                res=mapper.insertUser(bean);
 			}
 		} catch (Exception e) {
 		    e.printStackTrace();
             //手动事务回滚
             TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
 	    }
 		return res;
    }

    /**
    * 删除User
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int removeUser(User bean){
        int res=0;
        try {
            if(bean != null){
                res=mapper.deleteUser(bean);
            }
        } catch (Exception e) {
        //手动事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return res;
    }


    /**
    * 修改User
    */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int modifyUser(User bean){
        int res=0;
        try {
            if(bean != null){
                res=mapper.updateUser(bean);
            }
        } catch (Exception e) {
            //手动事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return res;
    }


    /**
    * 根据Map里的参数，查询User集合
    */
    @Override
    public List<User> queryUserByParams(Map params){
        List<User> list = null;
        try {
            if(params != null){
                 list=mapper.selectUserByParams(params);
            }
        } catch (Exception e) {
            //error
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public User login(Map params) {
        User user=null;
        try {
            if(params != null){
                user=mapper.login(params);
            }
        } catch (Exception e) {
            //error
            System.err.println(e.getMessage());
        }
        return user;
    }

}