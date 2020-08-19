package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.sellergoods.service.ItemCatService;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;

    @RequestMapping("/list")
    public PageResoult<TbItemCat> findALL(long grade){
       return itemCatService.findAll(grade);
    }

    @RequestMapping("/searchByCondition")
    public PageResoult<TbItemCat> searchByCondition(int currentPage,int pageSize,@RequestBody TbItemCat itemCat){
        return itemCatService.searchByCondition(currentPage,pageSize,itemCat);
    }

    @RequestMapping("/addItemCat")
    public Resoult addItemCat(@RequestBody TbItemCat itemCat){
        return itemCatService.addItemCat(itemCat);
    }

    @RequestMapping("/updateItemCat")
    public Resoult updateItemCat(@RequestBody TbItemCat itemCat){
        return itemCatService.updateItemCat(itemCat);
    }

    @RequestMapping("/deleteItemCat")
    public Resoult deleteItemCat(@RequestBody String[] selectedItems){
        return itemCatService.deleteItemCat(selectedItems);
    }

    @RequestMapping("/findItemCatByParentId")
    public PageResoult<TbItemCat> findItemCatByParentId(long parentId){
        return itemCatService.findItemCatByParentId(parentId);
    }

    @RequestMapping("/findOne")
    public TbItemCat findOne(long id){
        return itemCatService.findOne(id);
    }

    @RequestMapping("/findAllItemCat")
    public PageResoult<TbItemCat> findAllItemCat(){
        return itemCatService.findAllItemCat();
    }
}
