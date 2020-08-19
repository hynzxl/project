package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;

import java.util.List;
import java.util.Map;

public interface TypeTemplateService {
    PageResoult<TbTypeTemplate> findAll();

    PageResoult<TbTypeTemplate> findByPage(int currentPage, int pageSize);

    PageResoult<TbTypeTemplate> searchByCondition(int currentPage, int pageSize, String condition);

    Resoult addTypeTemplate(TbTypeTemplate typeTemplate);

    Resoult updateTypeTemplate(TbTypeTemplate typeTemplate);

    Resoult deleTemplate(String[] items);

    TbTypeTemplate findOne(long id);

    List<Map> findSpecificationDetail(long id);
}
