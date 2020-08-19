package com.pinyougou.group;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;

import java.io.Serializable;
import java.util.List;

//此类为商品总属性，包含TbGoods,TbGoodsDesc,TbItem三个表的内容
public class Goods implements Serializable {
    private TbGoods goods;
    private TbGoodsDesc goodsDesc;
    private List<TbItem> items;

    public Goods() {
    }

    public Goods(TbGoods goods, TbGoodsDesc goodsDesc, List<TbItem> items) {
        this.goods = goods;
        this.goodsDesc = goodsDesc;
        this.items = items;
    }

    public TbGoods getGoods() {
        return goods;
    }

    public void setGoods(TbGoods goods) {
        this.goods = goods;
    }

    public TbGoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(TbGoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<TbItem> getItems() {
        return items;
    }

    public void setItems(List<TbItem> items) {
        this.items = items;
    }
}
