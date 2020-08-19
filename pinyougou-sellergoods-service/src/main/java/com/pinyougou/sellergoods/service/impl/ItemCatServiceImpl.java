package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.pojo.TbItemCat;
import com.pinyougou.pojo.TbItemCatExample;
import com.pinyougou.sellergoods.service.ItemCatService;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Override
    public PageResoult<TbItemCat> findAll(long grade) {

        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(grade);
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(example);
        PageResoult<TbItemCat> resoult = new PageResoult<>();
        resoult.setRows(tbItemCats);
        return resoult;
    }

    @Override
    public PageResoult<TbItemCat> searchByCondition(int currentPage, int pageSize, TbItemCat itemCat) {
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        if (null != itemCat){   //设置查询条件
            if (itemCat.getParentId()!=null){
                criteria.andParentIdEqualTo(itemCat.getParentId());
            }
            if (itemCat.getName()!=null){
                criteria.andNameLike("%" + itemCat.getName() + "%");
            }
        }
        PageHelper.startPage(currentPage,pageSize);
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(example);
        Page<TbItemCat> itemCatPage = (Page<TbItemCat>) tbItemCats;
        return new PageResoult<>(itemCatPage.getTotal(),itemCatPage.getResult());
    }

    @Override
    public Resoult addItemCat(TbItemCat itemCat) {
        if (null != itemCat){
            int insert = itemCatMapper.insert(itemCat);
            if (insert>0){
                Resoult resoult = new Resoult("添加成功！");
                resoult.setItems(insert);
                return resoult;
            }
        }
        Resoult resoult = new Resoult("添加失败！");
        resoult.setItems(0);
        return resoult;
    }

    @Override
    public Resoult updateItemCat(TbItemCat itemCat) {
        if (null!=itemCat){
            int i = itemCatMapper.updateByPrimaryKey(itemCat);
            if (i>0){
                Resoult resoult = new Resoult("修改成功！");
                resoult.setItems(i);
                return resoult;
            }
        }
        Resoult resoult = new Resoult("修改失败！");
        resoult.setItems(0);
        return resoult;
    }

    @Override
    public Resoult deleteItemCat(String[] selectedItems) {
        int count=0;
        if (null != selectedItems && selectedItems.length >0){
            for (String selectedItem : selectedItems) {
                int i = itemCatMapper.deleteByPrimaryKey(new Long(selectedItem));
                if (i > 0){
                    count++;
                }
            }
            Resoult resoult = new Resoult("删除成功!");
            resoult.setItems(count);
            return resoult;
        }
        Resoult resoult = new Resoult("删除失败!");
        resoult.setItems(0);
        return resoult;
    }

    @Override
    public PageResoult<TbItemCat> findItemCatByParentId(long parentId) {
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(example);
        PageResoult<TbItemCat> resoult = new PageResoult<>();
        resoult.setRows(tbItemCats);
        return resoult;
    }

    @Override
    public TbItemCat findOne(long id) {
        return itemCatMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResoult<TbItemCat> findAllItemCat() {
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(null);
        PageResoult<TbItemCat> resoult = new PageResoult<>();
        resoult.setRows(tbItemCats);
        return resoult;
    }
}
