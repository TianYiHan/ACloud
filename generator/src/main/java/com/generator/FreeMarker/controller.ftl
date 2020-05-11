package com.generator.template.controller;//package自定义

import com.generator.template.entity.${ClassName};
import com.generator.template.service.${ClassName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
* Author:${Author}
* Date:${Date}
* Project:${Project}
* 表注释:${Comment}
*/
@RestController
@RequestMapping("/${ClassName?uncap_first}")
public class ${ClassName}Controller {

    @Autowired
    private ${ClassName}Service ${ClassName?uncap_first}Service;


    /**
    * 添加
    * @author:${Author}
    * @date: ${Date}
    * @return: int
    */
    @PostMapping(value = "/insert${ClassName}")
    public String insert${ClassName}(@RequestBody ${ClassName} bean){

        //do

        return "true";
    };


    /**
    * 删除${ClassName}
    * @param bean
    * @return
    */
    @DeleteMapping(value = "/delete${ClassName}")
    public String delete${ClassName}(@RequestBody ${ClassName} bean){

        //do

        return "true";

    };

    /**
    * 修改信息
    * @param bean
    * @return
    */
    @PutMapping(value = "/update${ClassName}")
    public String update${ClassName}(@RequestBody ${ClassName} bean){

        //do

        return "true";

    };

    /**
    * 查询信息
    * @param params
    * @return
    *get请求，参数不能是json(application/json;charset=utf-8)格式，只能是表单(application/x-www-form-urlencoded)格式
    */
    @PostMapping(value = "/select${ClassName}ByParams")
    public String select${ClassName}ByParams(@RequestBody Map params){

        //

        return "json List<${ClassName}>";
    };




}