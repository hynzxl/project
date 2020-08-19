package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;

public interface SellerService {
    TbSeller getSellerByUserId(String userId);

    Resoult registerSeller(TbSeller seller);

    PageResoult<TbSeller> searchByCondition(int currentPage, int pageSize, TbSeller seller);

    Resoult review(String statu, String sellerId);
}
