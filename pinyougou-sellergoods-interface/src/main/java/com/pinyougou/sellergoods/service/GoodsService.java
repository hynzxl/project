package com.pinyougou.sellergoods.service;

import com.pinyougou.group.Goods;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;

public interface GoodsService {
    Resoult updateGoods(Goods goods);

    Resoult addGoods(Goods goods);

    PageResoult<TbGoods> findAll();

    PageResoult<TbGoods> searchByCondition(int currentPage, int pageSize, TbGoods goods);

    Resoult deleteGoods(long[] items);

    Resoult review(String auditStatus, long[] items);

    Goods findOne(long id);

    Resoult deleteGoodsByManager(long[] items);

    Resoult superMarket(long id, String market);
}
