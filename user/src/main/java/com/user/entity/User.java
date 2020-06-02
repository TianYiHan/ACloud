package com.user.entity;//package自定义
import java.util.Date;

/**
* Author:https://github.com/TianYiHan
* Date:Mon May 25 10:42:41 CST 2020
* Project:ProjectName
* 表注释:
*/
public class User {

        private String id;    //用户唯一id
        private String name;    //用户名称
        private String level;    //用户等级
        private int money;  //用户金额
        private Date birthday;    //用户生日
        private String password;    //用户密码
        private String passwordsalt;    //用户密码加点盐
        private String email;    //用户邮箱
        private String emailstatus;    //邮箱是否验证
        private String mobile;    //用户电话
        private String mobilestatus;    //用户电话是否验证
        private String address;    //用户地址
        private Date createtime;    //用户创建时间
        private Date activetime;    //用户活跃时间（增删改查时更新）
        private String activeip;    //用户活跃ip（增删改查时更新）
        private String imgaddress;    //用户头像地址
        private String introduce;    //用户简介
        private int pwderrorcount; //用户登录密码错误次数




    public String getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public String getLevel() {
            return level;
        }
        public Date getBirthday() {
            return birthday;
        }
        public String getPassword() {
            return password;
        }
        public String getPasswordsalt() {
            return passwordsalt;
        }
        public String getEmail() {
            return email;
        }
        public String getEmailstatus() {
            return emailstatus;
        }
        public String getMobile() {
            return mobile;
        }
        public String getMobilestatus() {
            return mobilestatus;
        }
        public String getAddress() {
            return address;
        }
        public Date getCreatetime() {
            return createtime;
        }
        public Date getActivetime() {
            return activetime;
        }
        public String getImgaddress() {
            return imgaddress;
        }
        public String getIntroduce() {
            return introduce;
        }

        public void setId(String id) {
            this.id = id;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setLevel(String level) {
            this.level = level;
        }
        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public void setPasswordsalt(String passwordsalt) {
            this.passwordsalt = passwordsalt;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public void setEmailstatus(String emailstatus) {
            this.emailstatus = emailstatus;
        }
        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
        public void setMobilestatus(String mobilestatus) {
            this.mobilestatus = mobilestatus;
        }
        public void setAddress(String address) {
            this.address = address;
        }
        public void setCreatetime(Date createtime) {
            this.createtime = createtime;
        }
        public void setActivetime(Date activetime) {
            this.activetime = activetime;
        }
        public void setImgaddress(String imgaddress) {
            this.imgaddress = imgaddress;
        }
        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }


    public String getActiveip() {
        return activeip;
    }

    public void setActiveip(String activeip) {
        this.activeip = activeip;
    }



    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", money=" + money +
                ", birthday=" + birthday +
                ", password='" + password + '\'' +
                ", passwordsalt='" + passwordsalt + '\'' +
                ", email='" + email + '\'' +
                ", emailstatus='" + emailstatus + '\'' +
                ", mobile='" + mobile + '\'' +
                ", mobilestatus='" + mobilestatus + '\'' +
                ", address='" + address + '\'' +
                ", createtime=" + createtime +
                ", activetime=" + activetime +
                ", activeip='" + activeip + '\'' +
                ", imgaddress='" + imgaddress + '\'' +
                ", introduce='" + introduce + '\'' +
                '}';
    }

    public int getPwderrorcount() {
        return pwderrorcount;
    }

    public void setPwderrorcount(int pwderrorcount) {
        this.pwderrorcount = pwderrorcount;
    }
}