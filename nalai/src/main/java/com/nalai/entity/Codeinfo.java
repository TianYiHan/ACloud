package com.nalai.entity;//package自定义
import java.util.Date;

/**
* Author:https://github.com/TianYiHan
* Date:Fri May 22 09:15:27 CST 2020
* Project:ProjectName
* 表注释:
*/
public class Codeinfo {

        private int id;    //代码片段主键
        private String title;    //代码标题
        private String language;    //代码语言分类
        private String userid;    //创建此代码的用户id
        private String code;    //代码主体部分
        private Date createtime;    //创建时间
        private int nicecodecount;    //赞次数
        private int isok;    //是否合格1合格   0不合格 
        private String introduce;    //代码片段介绍



        public int getId() {
            return id;
        }
        public String getTitle() {
            return title;
        }
        public String getLanguage() {
            return language;
        }
        public String getUserid() {
            return userid;
        }
        public String getCode() {
            return code;
        }
        public Date getCreatetime() {
            return createtime;
        }
        public int getNicecodecount() {
            return nicecodecount;
        }
        public int getIsok() {
            return isok;
        }
        public String getIntroduce() {
            return introduce;
        }

        public void setId(int id) {
            this.id = id;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public void setLanguage(String language) {
            this.language = language;
        }
        public void setUserid(String userid) {
            this.userid = userid;
        }
        public void setCode(String code) {
            this.code = code;
        }
        public void setCreatetime(Date createtime) {
            this.createtime = createtime;
        }
        public void setNicecodecount(int nicecodecount) {
            this.nicecodecount = nicecodecount;
        }
        public void setIsok(int isok) {
            this.isok = isok;
        }
        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }


}