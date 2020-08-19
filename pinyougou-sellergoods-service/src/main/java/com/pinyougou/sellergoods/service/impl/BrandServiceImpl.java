package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mysql.cj.util.StringUtils;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.sellergoods.service.BrandService;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private TbBrandMapper brandMapper;
    @Override
    public PageResoult<TbBrand> findAll() {
        List<TbBrand> tbBrands = brandMapper.selectByExample(null);
        PageResoult<TbBrand> tbBrandPageResoult = new PageResoult<>();
        tbBrandPageResoult.setRows(tbBrands);
        return tbBrandPageResoult;
    }

    @Override
    public PageResoult<TbBrand> findByPage(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);//开始分页
        TbBrandExample example = new TbBrandExample();
        Page<TbBrand> brandPage = (Page<TbBrand>) brandMapper.selectByExample(example);
        PageResoult<TbBrand> resoult = new PageResoult<>(brandPage.getTotal(),brandPage.getResult());
        return resoult;
    }

    @Override
    public Resoult addBrand(TbBrand brand) {
        int insert = brandMapper.insert(brand);
        if (insert > 0){
           return new Resoult("添加成功！",insert);
        }
        return new Resoult("添加失败！");
    }

    @Override
    public Resoult updateBrand(TbBrand brand) {
        int i = brandMapper.updateByPrimaryKey(brand);
        if (i > 0){
            return new Resoult("修改品牌成功",i);
        }
        return new Resoult("修改失败！");
    }

    @Override
    public Resoult deleteBrand(String[] items) {
        int total = 0;
        System.out.println(items);
        if (null != items){
            for (String item : items) {
                int i = brandMapper.deleteByPrimaryKey(Long.parseLong(item));
                if (i > 0){
                    total++;
                }
            }
            return new Resoult("删除成功！",total);
        }
        return new Resoult("删除失败！",total);
    }

    @Override
    public PageResoult<TbBrand> search(int currentPage, int pageSize, TbBrand brand) {
        TbBrandExample example = new TbBrandExample();
        TbBrandExample.Criteria criteria = example.createCriteria();
        if (null != brand){
            if (!StringUtils.isEmptyOrWhitespaceOnly(brand.getName())){
                criteria.andNameLike("%" + brand.getName() + "%");
            }
            if (!StringUtils.isEmptyOrWhitespaceOnly(brand.getFirstChar())){
                criteria.andFirstCharEqualTo(brand.getFirstChar());
            }
        }
        PageHelper.startPage(currentPage,pageSize);
        Page<TbBrand> tbBrands = (Page<TbBrand>) brandMapper.selectByExample(example);
        PageResoult pageResoult = new PageResoult(tbBrands.getTotal(),tbBrands.getResult());
        return pageResoult;
    }

    @Override
    public List<Map<String, String>> findAllBrandToMap() {
        return brandMapper.findAllBrandToMap();
    }
}
