package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.group.SpecificationOption;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.sellergoods.service.SpecificationService;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private TbSpecificationMapper specificationMapper;
    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;
    @Override
    public PageResoult<TbSpecification> searchByCondition(int currentPage, int pageSize, String specificationName) {
        TbSpecificationExample example = new TbSpecificationExample();
        TbSpecificationExample.Criteria criteria = example.createCriteria();
        if (null != specificationName){
            criteria.andSpecNameLike("%" + specificationName + "%");
        }
        PageHelper.startPage(currentPage,pageSize);
        List<TbSpecification> tbSpecifications = specificationMapper.selectByExample(example);
        Page<TbSpecification> specificationPage = (Page<TbSpecification>) tbSpecifications;
        PageResoult<TbSpecification> resoult = new PageResoult<>(specificationPage.getTotal(), specificationPage.getResult());
        return resoult;
    }

    @Override
    public Resoult addSpecification(SpecificationOption specificationOption) {
        try{
            //1.先添加规格名字，获取对应的规格id,设置为规格选项options的外键
            specificationMapper.addSpecificationAndGetId(specificationOption.getSpecification());
            for (TbSpecificationOption tbSpecificationOption : specificationOption.getSpeOptions()) {
                tbSpecificationOption.setSpecId(specificationOption.getSpecification().getId());//设置规格选项options的外键
                specificationOptionMapper.insert(tbSpecificationOption);//插入数据库
            }
            return new Resoult("添加成功！");
        }catch (Exception e){
            return new Resoult("添加失败！");
        }

    }

    @Override
    public SpecificationOption findOne(long specId) {
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(specId);
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(specId);
        List<TbSpecificationOption> tbSpecificationOptions = specificationOptionMapper.selectByExample(example);
        return new SpecificationOption(tbSpecification,tbSpecificationOptions);
    }

    //修改规格
    @Override
    public Resoult updateSpecification(SpecificationOption specificationOption) {
        try {
            specificationMapper.updateByPrimaryKey(specificationOption.getSpecification());//更新规格表数据
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(specificationOption.getSpecification().getId());
            specificationOptionMapper.deleteByExample(example);//根据speId外键先将选项表对应的规格删除干净
            for (TbSpecificationOption tbSpecificationOption : specificationOption.getSpeOptions()) {
                tbSpecificationOption.setSpecId(specificationOption.getSpecification().getId());//设置规格选项options的外键
                specificationOptionMapper.insert(tbSpecificationOption);//重新插入选项表数据
            }
            return new Resoult("修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Resoult("修改数据失败！");
        }
    }

    @Override
    public Resoult delete(String[] selectedItems) {
        int items = 0;
        if (null != selectedItems){
            for (String selectedItem : selectedItems) {      //先根据specId删除规格绑定的选项记录
                TbSpecificationOptionExample example = new TbSpecificationOptionExample();
                TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
                criteria.andSpecIdEqualTo(new Long(selectedItem));
                specificationOptionMapper.deleteByExample(example);
                int i = specificationMapper.deleteByPrimaryKey(new Long(selectedItem));//再删除规格记录
                if (i>0){
                    items++;
                }
            }
        }
        return new Resoult("删除成功",items);
    }

    @Override
    public PageResoult<TbSpecification> findAll() {
        List<TbSpecification> tbSpecifications = specificationMapper.selectByExample(null);
        PageResoult<TbSpecification> tbSpecificationPageResoult = new PageResoult<>();
        tbSpecificationPageResoult.setRows(tbSpecifications);
        return tbSpecificationPageResoult;
    }

    @Override
    public List<Map<String, String>> findAllSpecificationToMap() {
        return specificationMapper.findAllSpecificationToMap();
    }


}
