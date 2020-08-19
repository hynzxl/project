package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;

public interface ItemCatService {
    PageResoult<TbItemCat> findAll(long grade);

    PageResoult<TbItemCat> searchByCondition(int currentPage, int pageSize, TbItemCat itemCat);

    Resoult addItemCat(TbItemCat itemCat);

    Resoult updateItemCat(TbItemCat itemCat);

    Resoult deleteItemCat(String[] selectedItems);

    PageResoult<TbItemCat> findItemCatByParentId(long parentId);

    TbItemCat findOne(long id);

    PageResoult<TbItemCat> findAllItemCat();
}
