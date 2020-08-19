package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;

import java.util.List;
import java.util.Map;


public interface BrandService {
    public PageResoult<TbBrand> findAll();

    PageResoult<TbBrand> findByPage(int currentPage, int pageSize);

    Resoult addBrand(TbBrand brand);

    Resoult updateBrand(TbBrand brand);

    Resoult deleteBrand(String[] items);

    PageResoult<TbBrand> search(int currentPage, int pageSize, TbBrand brand);

    List<Map<String, String>> findAllBrandToMap();
}
