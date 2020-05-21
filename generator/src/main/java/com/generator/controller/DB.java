package com.generator.controller;

/**
 * Author:HanTianYi
 * Date:2020/4/27 15:52
 * Project:ACloud
 * package:com.generator.controller
 */

public class DB {

    String Field;
    String Comment;
    String Type;
    String Null;
    String Extra;
    String Privileges;
    String Collation;
    String Default;
    String Key;
    



    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getNull() {
        return Null;
    }

    public void setNull(String aNull) {
        Null = aNull;
    }

    public String getExtra() {
        return Extra;
    }

    public void setExtra(String extra) {
        Extra = extra;
    }

    public String getPrivileges() {
        return Privileges;
    }

    public void setPrivileges(String privileges) {
        Privileges = privileges;
    }

    public String getCollation() {
        return Collation;
    }

    public void setCollation(String collation) {
        Collation = collation;
    }

    public String getDefault() {
        return Default;
    }

    public void setDefault(String aDefault) {
        Default = aDefault;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
