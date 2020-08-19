package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/shop")
public class ShopController {
    @Reference
    private SellerService sellerService;

    @RequestMapping("/searchByCondition")
    public PageResoult<TbSeller> searchByCondition(int currentPage,int pageSize,@RequestBody TbSeller seller){
        return sellerService.searchByCondition(currentPage,pageSize,seller);
    }

    @RequestMapping("/review")
    public Resoult review(String statu,String sellerId){
        return sellerService.review(statu,sellerId);
    }


}
