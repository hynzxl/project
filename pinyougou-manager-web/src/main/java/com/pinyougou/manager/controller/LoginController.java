package com.pinyougou.manager.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    //查找当前登录的用户
    @RequestMapping("/findLoginUser")
    public Map<String,String> findLoginUser(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String,String> mapName = new HashMap<>();
        mapName.put("username",name);
        return mapName;
    }
}
