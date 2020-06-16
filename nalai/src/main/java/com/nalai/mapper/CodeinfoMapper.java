package com.nalai.mapper;

import com.nalai.entity.Codeinfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
* Author:https://github.com/TianYiHan
* Date:Fri May 22 09:15:27 CST 2020
* 表注释:
*/
@Component
public interface CodeinfoMapper {

    /**
    * 插入Codeinfo(非空)
    * @param bean
    * @return
    */
    public int insertCodeinfo(Codeinfo bean);

    /**
    * 删除Codeinfo
    *
    * @param bean
    * @return
    */
    public int deleteCodeinfo(Codeinfo bean);

    /**
    * 修改信息
    *
    * @param bean
    * @return
    */
    public int updateCodeinfo(Codeinfo bean);

    /**
    * 查询信息
    *
    * @param params
    * @return
    */
    public List<Codeinfo> selectCodeinfoByParams(Map params);



}