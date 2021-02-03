package com.java2e.martin.erd.controller;

import com.java2e.martin.erd.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author 狮少
 * @version 1.0
 * @date 2020/10/16
 * @describtion UserController
 * @since 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public List queryUsers() {
        return userMapper.selectByMap(new HashMap<>());
    }


    public static void main(String[] args) {

    }

}
