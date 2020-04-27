package com.generator.FreeMarker;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Author:HanTianYi
 * Date:2020/4/23 15:13
 * Project:ACloud
 * package:com.generator.FreeMarker
 */
public class FreeMarkerDemo {

    private static final String TEMPLATE_PATH = "C:\\Users\\13954\\IdeaProjects\\ACloud\\generator\\src\\main\\java\\com\\generator\\template";
    private static final String CLASS_PATH = "C:\\Users\\13954\\IdeaProjects\\ACloud\\generator\\src\\main\\java\\c om\\generator\\code";

    public static void main(String[] args) {
        // step1 创建freeMarker配置实例
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File("src\\main\\java\\com\\generator\\template\""));
            // step3 创建数据模型
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("classPath", "com.generator.code");
            dataMap.put("className", "AutoCodeDemo");
            dataMap.put("helloWorld", "通过简单的 <代码自动生产程序> 演示 FreeMarker的HelloWorld！");
            // step4 加载模版文件
            Template template = configuration.getTemplate("entity.ftl");
            // step5 生成数据
            File docFile = new File(CLASS_PATH + "\\" + "AutoCodeDemo.java");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // step6 输出文件
            template.process(dataMap, out);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^AutoCodeDemo.java 文件创建成功 !");
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
