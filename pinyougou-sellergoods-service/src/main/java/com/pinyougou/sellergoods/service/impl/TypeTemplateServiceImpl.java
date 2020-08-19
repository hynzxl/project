package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.mapper.TbTypeTemplateMapper;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.pojo.TbTypeTemplateExample;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {
    @Autowired
    private TbTypeTemplateMapper tbTypeTemplateMapper;
    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;

    @Override
    public PageResoult<TbTypeTemplate> findAll() {
        List<TbTypeTemplate> tbTypeTemplates = tbTypeTemplateMapper.selectByExample(null);
        PageResoult<TbTypeTemplate> tbTypeTemplatePageResoult = new PageResoult<>();
        tbTypeTemplatePageResoult.setRows(tbTypeTemplates);
        return tbTypeTemplatePageResoult;
    }

    //分页查询
    @Override
    public PageResoult<TbTypeTemplate> findByPage(int currentPage, int pageSize) {
        TbTypeTemplateExample example = new TbTypeTemplateExample();
        PageHelper.startPage(currentPage,pageSize);
        List<TbTypeTemplate> tbTypeTemplates = tbTypeTemplateMapper.selectByExample(example);
        Page<TbTypeTemplate> typeTemplatePage = (Page<TbTypeTemplate>) tbTypeTemplates;
        PageResoult<TbTypeTemplate> resoult = new PageResoult<>(typeTemplatePage.getTotal(),typeTemplatePage.getResult());
        return resoult;
    }

    @Override
    public PageResoult<TbTypeTemplate> searchByCondition(int currentPage, int pageSize, String condition) {
        TbTypeTemplateExample example = new TbTypeTemplateExample();
        TbTypeTemplateExample.Criteria criteria = example.createCriteria();
        if (null != condition){  //设置条件
            criteria.andNameLike("%" + condition + "%");
        }
        PageHelper.startPage(currentPage,pageSize);
        List<TbTypeTemplate> tbTypeTemplates = tbTypeTemplateMapper.selectByExample(example);
        Page<TbTypeTemplate> typeTemplatePage = (Page<TbTypeTemplate>) tbTypeTemplates;
        PageResoult<TbTypeTemplate> resoult = new PageResoult<>(typeTemplatePage.getTotal(),typeTemplatePage.getResult());
        return resoult;
    }

    @Override
    public Resoult addTypeTemplate(TbTypeTemplate typeTemplate) {
        int insert = tbTypeTemplateMapper.insert(typeTemplate);
        if (insert>0){
            return new Resoult("新增成功！",insert);
        }
        return new Resoult("新增失败！");
    }

    @Override
    public Resoult updateTypeTemplate(TbTypeTemplate typeTemplate) {
        int i = tbTypeTemplateMapper.updateByPrimaryKey(typeTemplate);
        if (i > 0){
            return new Resoult("修改模板成功!");
        }
        return new Resoult("修改模板失败!");
    }

    @Override
    public Resoult deleTemplate(String[] items) {
        int itemSum = 0;
        if (null !=items){
            for (String item : items) {
                int i = tbTypeTemplateMapper.deleteByPrimaryKey(new Long(item));
                if (i > 0){
                    itemSum++;
                }
            }
            return new Resoult("删除成功！",itemSum);
        }
        return new Resoult("删除失败！",itemSum);
    }

    @Override
    public TbTypeTemplate findOne(long id) {
        return tbTypeTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map> findSpecificationDetail(long id) {
        TbTypeTemplate tbTypeTemplate = tbTypeTemplateMapper.selectByPrimaryKey(id);
        String specIds = tbTypeTemplate.getSpecIds();
        List<Map> maps = JSON.parseArray(specIds, Map.class);//获取规格项，并将规格转换为json对象
        for (Map map : maps) {
            long specId = Long.parseLong(map.get("id")+"");  //获取规格的id
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(specId);
            List<TbSpecificationOption> tbSpecificationOptions = specificationOptionMapper.selectByExample(example);//根据规格id查询出具体的选项
            map.put("attributeValue",tbSpecificationOptions);  //将查询好的规格选项放到返回的map集合中
        }
        return maps;
    }
}
