package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

    @Reference
    private TypeTemplateService typeTemplateService;

    @RequestMapping("/list")
    public PageResoult<TbTypeTemplate> findAll(){
        return typeTemplateService.findAll();
    }

    //分页查询所有
    @RequestMapping("/findByPage")
    public PageResoult<TbTypeTemplate> findByPage(int currentPage,int pageSize){
        return typeTemplateService.findByPage(currentPage,pageSize);
    }

    //分页查询带条件
    @RequestMapping("/searchByCondition")
    public PageResoult<TbTypeTemplate> searchByCondition(int currentPage,int pageSize,String condition){
        return typeTemplateService.searchByCondition(currentPage,pageSize,condition);
    }

    //保存新增模板
    @RequestMapping("/addTypeTemplate")
    public Resoult addTypeTemplate(@RequestBody TbTypeTemplate typeTemplate){
        return typeTemplateService.addTypeTemplate(typeTemplate);
    }
    //修改模板
    @RequestMapping("/updateTypeTemplate")
    public Resoult updateTypeTemplate(@RequestBody TbTypeTemplate typeTemplate){
        return typeTemplateService.updateTypeTemplate(typeTemplate);
    }

    //批量删除数据
    @RequestMapping("/deleTemplate")
    public Resoult deleTemplate(@RequestBody String[] items){
        return typeTemplateService.deleTemplate(items);
    }

}
