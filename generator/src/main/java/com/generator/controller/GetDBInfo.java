package com.generator.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.*;

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



    @RequestMapping("/getTableinfo")
    public String getTableinfo(@RequestParam Map<String,String> map) throws SQLException {
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
            List<Map> list =new ArrayList<>();

            while (rs.next()){
                System.err.println(rs.getString(1));

                ResultSet rs2=connection.createStatement().executeQuery("show full columns from `"+rs.getString(1)+"`" );
                Map<String,Map<String,String>> tableinfo =new HashMap<>();


                while (rs2.next()){

                    Map<String,String> info =new HashMap<>();
                    info.put("Field",rs2.getString("Field"));
                    info.put("Type",rs2.getString("Type"));
                    info.put("Null",rs2.getString("Null"));
                    info.put("Key",rs2.getString("Key"));
                    info.put("Default",rs2.getString("Default"));
                    info.put("Extra",rs2.getString("Extra"));
                    info.put("Collation",rs2.getString("Collation"));
                    info.put("Privileges",rs2.getString("Privileges"));
                    info.put("Comment",rs2.getString("Comment"));

                    tableinfo.put(rs2.getString("Field"),info);
                }
                Map<String,Map<String,Map<String,String>>> res=new HashMap<>();
                res.put(rs.getString(1),tableinfo);
                list.add(res);

            }
            return JSON.toJSONString(list);

        }catch (Exception e){
            return e.getMessage();
        }finally {
            if (rs!=null){
                rs.close();
                System.out.println("rs.close();");
            }
            if (stmt!=null){
                stmt.close();
                System.out.println("stmt.close();");
            }
            if (connection!=null){
                connection.close();
                System.out.println("connection.close();");

            }

        }
    }

}
