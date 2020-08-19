package com.pinyougou.shop.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.ArrayList;
import java.util.List;

public class UserAuthentication implements UserDetailsService {

    private SellerService sellerService;

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        TbSeller seller = sellerService.getSellerByUserId(userId);
        if (null != seller){
            System.out.println(seller.getName() + seller.getStatus());
            if (seller.getStatus().equals("1")){
                List<GrantedAuthority> authorities = new ArrayList<>();
                SimpleGrantedAuthority role_admin = new SimpleGrantedAuthority("ROLE_ADMIN");
                authorities.add(role_admin);
                return new User(userId,seller.getPassword(),authorities);
            }else {
                return null;
            }
        }
        return null;
    }
}
