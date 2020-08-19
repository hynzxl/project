package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import com.pinyougou.utils.Resoult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/seller")
public class SellerController {
    @Reference
    private SellerService shopService;

    @RequestMapping("/registerSeller")
    public Resoult registerSeller(@RequestBody TbSeller seller){
        try {
            //1.设置注册用户的状态为待审核，1为待审核状态
            seller.setStatus("0");
            //2.将用户的密码加密
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            seller.setPassword(encoder.encode(seller.getPassword()));
            return shopService.registerSeller(seller);
        } catch (Exception e) {
            e.printStackTrace();
            return new Resoult("注册失败!");
        }
    }
    @RequestMapping("/findSeller")
    public Map<String,String> findSeller(){

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, String> map = new HashMap<>();
        map.put("username",name);
        return map;
    }
}
