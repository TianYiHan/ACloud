package com.nalai.service;

import com.nalai.entity.Codeinfo;

import java.util.List;
import java.util.Map;

/**
* Author:https://github.com/TianYiHan
* Date:Fri May 22 09:15:27 CST 2020
* 表注释:
*/
public interface CodeinfoService {

    /**
    * 插入Codeinfo(非空)
    * @param bean
    * @return
    */
    public int addCodeinfo(Codeinfo bean);

    /**
    * 删除Codeinfo
    *
    * @param bean
    * @return
    */
    public int  removeCodeinfo(Codeinfo bean);

    /**
    * 修改Codeinfo
    *
    * @param bean
    * @return
    */
    public int  modifyCodeinfo(Codeinfo bean);

    /**
    * 查询Codeinfo
    *
    * @param params
    * @return
    */
    public List<Codeinfo> queryCodeinfoByParams(Map params);




}