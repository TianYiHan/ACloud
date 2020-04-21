package com.user.controller;

import com.user.entity.Comment;
import com.user.mapper.CommentMapper;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author:HanTianYi
 * Date:2020/4/21 09:54
 * Project:ACloud
 * package:com.user.controller
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private CommentMapper commentMapper;

    @RequestMapping("/comment")
    public String testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<Comment> userList = commentMapper.selectList(null);
        System.out.println(userList.size());
        System.out.println(userList.toString());


        return "a";
    }

}
