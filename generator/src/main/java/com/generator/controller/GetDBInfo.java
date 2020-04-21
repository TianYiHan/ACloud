package com.generator.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
/**
 * Author:HanTianYi
 * Date:2020/4/21 11:13
 * Project:ACloud
 * package:com.generator.controller
 * 代码生成器的核心开发流程
 * 如何连接数据库，获取数据库信息，以及根据数据库的表字段信息如何转换成java实体类型
 * 1）获取数据库表信息
 * 2）数据库表信息转java类型
 * 配置必须的基本数据，根据模版语言编写代码模版，根据模版生成代码文件(我这里用freemarker模版语言)
 * 1）配置数据库类型，帐号，密码，需要生成的表的基本信息
 * 2）编写模版，根据配置的信息注入模版生成代码文件以及生成代码文件的路径
 */
@RestController
@CrossOrigin
@RequestMapping("DB")
public class GetDBInfo {

    @RequestMapping("/getTables")
    public String getTables(@RequestParam Map<String,String> map) throws SQLException {
        System.out.println("连接数据库："+map.toString());
        Connection connection =null;
        Statement stmt =null;
        ResultSet rs =null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (Exception e){
            return e.getMessage();
        }
        try {
            String host=map.get("host");
            String database=map.get("database");
            String port=map.get("port");
            String username = map.get("username");
            String password = map.get("password");
            String url = "jdbc:mysql://"+host+":"+port+"/"+database+"";

            connection = DriverManager.getConnection(url,username,password);
            stmt = connection.createStatement();
            // 4.Statement->ResultSet
            String sql = "show tables from "+database+"";
            rs = stmt.executeQuery(sql);
            List<String> list =new ArrayList<String>();
            while (rs.next()){
                list.add(rs.getString(1));
            }
            return JSON.toJSONString(list);

        }catch (Exception e){
            return e.getMessage();
        }finally {
            if (rs!=null){
                rs.close();
            }
            if (stmt!=null){
                stmt.close();
            }
            if (connection!=null){
                connection.close();
            }

        }
    }


}
