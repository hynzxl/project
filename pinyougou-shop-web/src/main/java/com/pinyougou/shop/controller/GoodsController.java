package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.group.Goods;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.GoodsService;
import com.pinyougou.sellergoods.service.SellerService;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;
    @Reference
    private SellerService sellerService;

    @RequestMapping("/updateGoods")
    public Resoult updateGoods(@RequestBody Goods goods){
      return  goodsService.updateGoods(goods);
    }

    @RequestMapping("/addGoods")
    private Resoult addGoods(@RequestBody Goods goods){
        //获取商家的id
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        //为goods设置商家id
        goods.getGoods().setSellerId(name);
        return goodsService.addGoods(goods);
    }
    @RequestMapping("/findAll")
    public PageResoult<TbGoods> findAll(){
        return goodsService.findAll();
    }
    @RequestMapping("/searchByCondition")
    public PageResoult<TbGoods> searchByCondition(int currentPage,int pageSize,@RequestBody TbGoods goods){
        //为该goods设置当前的商家名
        goods.setSellerId(SecurityContextHolder.getContext().getAuthentication().getName());
        return goodsService.searchByCondition(currentPage,pageSize,goods);
    }
    @RequestMapping("/deleteGoods")
    public Resoult deleteGoods(@RequestBody long[] items){
      return goodsService.deleteGoods(items);
    }
    @RequestMapping("/review")
    public Resoult review(String auditStatus,@RequestBody long[] items){
        return goodsService.review(auditStatus,items);
    }
    @RequestMapping("/findOne")
    public Goods findOne(long id){
        return goodsService.findOne(id);
    }

    @RequestMapping("/superMarket")
    public Resoult superMarket(long id,String market){
        return goodsService.superMarket(id,market);
    }
}
