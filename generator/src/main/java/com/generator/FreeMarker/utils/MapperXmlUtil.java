package com.generator.FreeMarker.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;

/**
 * Author:HanTianYi
 * Date:2020/5/9 16:33
 * Project:ACloud
 * package:com.generator.FreeMarker.utils
 */
public class MapperXmlUtil {

    /***
     *
     * @Title: createMapper
     * @Description: TODO 创建MapperXML
     */
    public static void createMapperXML(Map<String,Object> map2){
        // step1 创建freeMarker配置实例
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File("generator/src/main/java/com/generator/FreeMarker"));
            // step3 创建数据模型
            //Map<String,Object> map

            // step4 加载模版文件
            Template template = configuration.getTemplate("mapperxml.ftl");
            // step5 生成数据
            File docFile = new File("generator/src/main/java/com/generator/template/mapperxml" + "/" + map2.get("ClassName")+"Mapper.xml");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // step6 输出文件
            template.process(map2, out);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^"+map2.get("ClassName")+"Mapper.xml 文件创建成功 !");
        } catch (Exception e) {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
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
