package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.group.SpecificationOption;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.sellergoods.service.SpecificationService;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specification")
public class SpecificationController {

    @Reference
    private SpecificationService specificationService;

    @RequestMapping("/list")
    public PageResoult<TbSpecification> findAll(){
        return specificationService.findAll();
    }
    @RequestMapping("/searchByCondition")
    public PageResoult<TbSpecification> searchByCondition(int currentPage,int pageSize,String specificationName){
        return specificationService.searchByCondition(currentPage,pageSize,specificationName);
    }
    //添加规格
    @RequestMapping("/addSpecification")
    public Resoult addSpecification(@RequestBody SpecificationOption specificationOption){
        return specificationService.addSpecification(specificationOption);
    }

    @RequestMapping("/findOne")
    public SpecificationOption findOne(long specId){
        return specificationService.findOne(specId);
    }

    @RequestMapping("/updateSpecification")
    public Resoult updateSpecification(@RequestBody SpecificationOption specificationOption){
        return specificationService.updateSpecification(specificationOption);
    }
    @RequestMapping("/delete")
    public Resoult delete(@RequestBody String[] selectedItems){
        return specificationService.delete(selectedItems);
    }

    //查询所有规格为map
    @RequestMapping("/findAllSpecificationToMap")
    public List<Map<String,String>> findAllSpecificationToMap(){
        return specificationService.findAllSpecificationToMap();
    }
}
