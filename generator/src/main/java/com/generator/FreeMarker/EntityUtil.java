package com.generator.FreeMarker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Author:HanTianYi
 * Date:2020/4/23 15:49
 * Project:ACloud
 * package:com.generator.template.entity
 */
public class EntityUtil {

    private static final String CLASS_PATH = "C:\\Users\\13954\\IdeaProjects\\ACloud\\generator\\src\\main\\java\\com\\generator\\code";

    /***
     *
     * @Title: createEntity
     * @Description: TODO 创建实体类
     */
    public static void createEntity(Map<String,Object> map){
        // step1 创建freeMarker配置实例
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File("generator/src/main/java/com/generator/template/entity"));
            // step3 创建数据模型
            //Map<String,Object> map

            // step4 加载模版文件
            Template template = configuration.getTemplate("entity.ftl");
            // step5 生成数据
            File docFile = new File("generator/src/main/java/com/generator/template/entity" + "/" + map.get("ClassName"));
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // step6 输出文件
            template.process(map, out);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^"+map.get("ClassName")+".java 文件创建成功 !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }





}
