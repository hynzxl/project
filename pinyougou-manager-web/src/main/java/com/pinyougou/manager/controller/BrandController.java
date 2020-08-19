package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/brand")
public class BrandController {
    @Reference
    private BrandService brandService;

    @RequestMapping("/list")
    public PageResoult<TbBrand> findAll(){
        return brandService.findAll();
    }

    @RequestMapping("/findByPage")
    public PageResoult<TbBrand> findByPage(int currentPage,int pageSize){
        return brandService.findByPage(currentPage,pageSize);
    }

    //添加brand
    @RequestMapping("/addBrand")
    public Resoult addBrand(@RequestBody TbBrand brand){
        return brandService.addBrand(brand);
    }
    //修改brand
    @RequestMapping("/updateBrand")
    public Resoult updateBrand(@RequestBody TbBrand brand){
        return brandService.updateBrand(brand);
    }
    //删除Brand
    @RequestMapping("/deleteBrand")
    public Resoult deleteBrand(@RequestBody String[] items){
        return brandService.deleteBrand(items);
    }
    //条件查询带分页
    @RequestMapping("/search")
    public PageResoult<TbBrand> search(int currentPage,int pageSize,@RequestBody(required = false) TbBrand brand){
        System.out.println(currentPage);
        System.out.println(pageSize);
        if (null != brand){
            System.out.println(brand.getName());
            System.out.println(brand.getFirstChar());
        }

        return brandService.search(currentPage,pageSize,brand);
    }

    //查询品牌。转换为Map，用于select2
    @RequestMapping("/findAllBrandToMap")
    public List<Map<String,String>> findAllBrandToMap(){
        return brandService.findAllBrandToMap();
    }
}
