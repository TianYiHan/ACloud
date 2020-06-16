package com.nalai.controller;

import com.nalai.entity.Codeinfo;
import com.nalai.service.CodeinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
* Author:https://github.com/TianYiHan
* Date:Fri May 22 09:15:27 CST 2020
* Project:ProjectName
* 表注释:
 * */
@RestController
@RequestMapping("/codeinfo")
public class CodeinfoController {

    @Autowired
    private CodeinfoService codeinfoService;


    /**
    * 添加
    * @author:https://github.com/TianYiHan
    * @date: Fri May 22 09:15:27 CST 2020
    * @return: int
    */
    @PostMapping(value = "/insertCodeinfo")
    public String insertCodeinfo(@RequestBody Codeinfo bean){

        //do

        return "true";
    };


    /**
    * 删除Codeinfo
    * @param bean
    * @return
    */
    @DeleteMapping(value = "/deleteCodeinfo")
    public String deleteCodeinfo(@RequestBody Codeinfo bean){

        //do

        return "true";

    };

    /**
    * 修改信息
    * @param bean
    * @return
    */
    @PutMapping(value = "/updateCodeinfo")
    public String updateCodeinfo(@RequestBody Codeinfo bean){

        //do

        return "true";

    };

    /**
    * 查询信息
    * @param params
    * @return
    *get请求，参数不能是json(application/json;charset=utf-8)格式，只能是表单(application/x-www-form-urlencoded)格式
    */
    @PostMapping(value = "/selectCodeinfoByParams")
    public String selectCodeinfoByParams(@RequestBody Map params){

        //

        return "json List<Codeinfo>";
    };




}