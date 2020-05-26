package com.nalai.entity; //package自定义
import java.util.Date;

/**
* Author:https://github.com/TianYiHan
* Date:Fri May 22 09:15:27 CST 2020
* Project:ProjectName
* 表注释:
*/
public class Message {

        private int id;    //留言id
        private String userid;    //留言的用户
        private String content;    //留言内容
        private Date time;    //留言时间
        private int parentmessage;    //父级留言，有此属性说明是回复



        public int getId() {
            return id;
        }
        public String getUserid() {
            return userid;
        }
        public String getContent() {
            return content;
        }
        public Date getTime() {
            return time;
        }
        public int getParentmessage() {
            return parentmessage;
        }

        public void setId(int id) {
            this.id = id;
        }
        public void setUserid(String userid) {
            this.userid = userid;
        }
        public void setContent(String content) {
            this.content = content;
        }
        public void setTime(Date time) {
            this.time = time;
        }
        public void setParentmessage(int parentmessage) {
            this.parentmessage = parentmessage;
        }


}