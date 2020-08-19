package com.pinyougou.sellergoods.service;

import com.pinyougou.group.SpecificationOption;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;

import java.util.List;
import java.util.Map;

public interface SpecificationService {
    PageResoult<TbSpecification> searchByCondition(int currentPage, int pageSize, String specificationName);

    Resoult addSpecification(SpecificationOption specificationOption);

    SpecificationOption findOne(long specId);

    Resoult updateSpecification(SpecificationOption specificationOption);

    Resoult delete(String[] selectedItems);

    PageResoult<TbSpecification> findAll();

    List<Map<String, String>> findAllSpecificationToMap();
}
