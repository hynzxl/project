package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSellerMapper;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.pojo.TbSellerExample;
import com.pinyougou.sellergoods.service.SellerService;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private TbSellerMapper sellerMapper;
    @Override
    public TbSeller getSellerByUserId(String userId) {
        TbSeller seller = sellerMapper.selectByPrimaryKey(userId);
        return seller;
    }

    @Override
    public Resoult registerSeller(TbSeller seller) {
        int insert = sellerMapper.insert(seller);
        if (insert > 0){
            Resoult resoult = new Resoult("注册成功!");
            resoult.setCome(1);
            return resoult;
        }else {
            Resoult resoult = new Resoult("注册失败!");
            resoult.setCome(0);
            return resoult;
        }
    }

    @Override
    public PageResoult<TbSeller> searchByCondition(int currentPage, int pageSize, TbSeller seller) {
        TbSellerExample example = new TbSellerExample();
        TbSellerExample.Criteria criteria = example.createCriteria();
        if (null != seller) {
            if (seller.getName() != null){
                criteria.andNameLike("%" + seller.getName() + "%");
            }
            if (seller.getNickName() != null){
                criteria.andNickNameLike("%" + seller.getNickName() + "%");
            }
            if (seller.getStatus() != null){
                criteria.andStatusEqualTo(seller.getStatus());
            }
        }
        PageHelper.startPage(currentPage,pageSize);
        List<TbSeller> tbSellers = sellerMapper.selectByExample(example);
        Page<TbSeller> pages = (Page<TbSeller>) tbSellers;
        return new PageResoult<>(pages.getTotal(),pages.getResult());
    }

    @Override
    public Resoult review(String statu, String sellerId) {
        TbSeller seller = sellerMapper.selectByPrimaryKey(sellerId);
        seller.setStatus(statu);
        int i = sellerMapper.updateByPrimaryKey(seller);
        if (i > 0){
            return new Resoult("修改成功！");
        }
        return new Resoult("修改失败！");
    }
}
