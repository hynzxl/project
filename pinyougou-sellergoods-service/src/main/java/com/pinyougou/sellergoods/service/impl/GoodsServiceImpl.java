package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.group.Goods;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.sellergoods.service.GoodsService;
import com.pinyougou.utils.PageResoult;
import com.pinyougou.utils.Resoult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private TbGoodsMapper goodsMapper;
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Autowired
    private TbSellerMapper sellerMapper;
    @Autowired
    private TbBrandMapper brandMapper;
    @Autowired
    private TbItemMapper itemMapper;

    @Override
    @Transactional
    public Resoult updateGoods(Goods goods) {
        Resoult resoult = new Resoult("更新失败！");  //初始返回信息
        resoult.setItems(0);                        //初始新增数据
        if (null != goods){
            //1.先更新Tbgoods表
            //1.1拿到goods中tbGoods的数据
            TbGoods goods1 = goods.getGoods();
            //1.3设置audit_status的值为0代表状态，待提交审核的状态
            goods1.setAuditStatus("0");
            //1.4设置模板id的值,通过三级分类的id查询出来
            TbItemCat tbItemCat = itemCatMapper.selectByPrimaryKey(goods1.getCategory3Id());
            goods1.setTypeTemplateId(tbItemCat.getTypeId());
            //1.5设置完成，插入表tb-goods
            int insert = goodsMapper.updateByPrimaryKey(goods1);
            if (insert>0){       //2.更新tb-goods表成功，下一步更新表tb-goodsDesc
                //2.1从goods对象中获取插入tb_goodsdesc表的对象
                TbGoodsDesc goodsDesc = goods.getGoodsDesc();
                //2.3更新表tb_goodsdesc
                int insert1 = goodsDescMapper.updateByPrimaryKey(goodsDesc);
                if (insert1 > 0){//3.更新tb-goodsDesc表成功，再更新表tb-item，此表为一对多，先根据id将原来的数据删除，再添加
                    //3.0根据id删除原来tb-item表的数据
                    TbItemExample example = new TbItemExample();
                    TbItemExample.Criteria criteria = example.createCriteria();
                    criteria.andGoodsIdEqualTo(goods1.getId());
                    itemMapper.deleteByExample(example);

                    //3.1获取插入的对象
                    List<TbItem> items = goods.getItems();
                    for (TbItem item : items) {
                        //3.2设置goodsid
                        item.setGoodsId(goods1.getId());
                        //3.3设置商家的名称和id
                        item.setSellerId(goods1.getSellerId());
                        TbSeller seller = sellerMapper.selectByPrimaryKey(goods1.getSellerId());
                        item.setSeller(seller.getNickName());
                        //3.4设置brand品牌
                        Long brandId = goods1.getBrandId();
                        TbBrand tbBrand = brandMapper.selectByPrimaryKey(brandId);
                        item.setBrand(tbBrand.getName());
                        //3.5设置分类，三级分类，通过上面查询出的三级分类设置
                        item.setCategory(tbItemCat.getName());
                        //3.6设置三级分类的id
                        item.setCategoryid(goods1.getCategory3Id());
                        //3.7设置创建时间和更新时间，更新不需要创建时间
                        item.setCreateTime(new Date());
                        //3.8设置更新时间
                        item.setUpdateTime(new Date());
                        //3.9设置图片,通过goodsDesc中的imageItems获取第一张图片
                        String itemImages = goodsDesc.getItemImages();
                        List<Map> maps = JSON.parseArray(itemImages, Map.class);
                        Map map = maps.get(0);
                        item.setImage((String) map.get("url"));
                        //3.10设置title，设置为goodsName
                        item.setTitle(goods1.getGoodsName());
                        //3.10设置完成，将其添加到tb-items中
                        itemMapper.insert(item);
                    }
                }
                resoult.setMessage("添加成功!");
                resoult.setItems(insert);
            }
        }
        return resoult;
    }

    @Override
    @Transactional
    public Resoult addGoods(Goods goods) {
        Resoult resoult = new Resoult("新增失败！");  //初始返回信息
        resoult.setItems(0);                        //初始新增数据
        if (null != goods){
            //1.先插入Tbgoods表，获取插入之后的id
            //1.1拿到goods中tbGoods的数据
            TbGoods goods1 = goods.getGoods();
            /*//1.2获取商家的名称，然后设置列seller_id
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            goods1.setSellerId(name);*/
            //1.3设置audit_status的值为0代表状态
            goods1.setAuditStatus("0");
            //1.4设置模板id的值,通过三级分类的id查询出来
            TbItemCat tbItemCat = itemCatMapper.selectByPrimaryKey(goods1.getCategory3Id());
            goods1.setTypeTemplateId(tbItemCat.getTypeId());
            //1.5设置完成，插入表tb-goods
            int insert = goodsMapper.addGoods(goods1);
            if (insert>0){       //2.插入tb-goods表成功，获取插入之后的goods1id
                //2.1从goods对象中获取插入tb_goodsdesc表的对象
                TbGoodsDesc goodsDesc = goods.getGoodsDesc();
                //2.2设置goodsDesc的id
                goodsDesc.setGoodsId(goods1.getId());
                //2.3插入表tb_goodsdesc
                int insert1 = goodsDescMapper.insert(goodsDesc);

                if (insert1 > 0){//3.插入tb-goodsDesc表成功，在插入tb_items表
                    //3.1获取插入的对象
                    List<TbItem> items = goods.getItems();
                    for (TbItem item : items) {
                        //3.2设置goodsid
                        item.setGoodsId(goods1.getId());
                        //3.3设置商家的名称和id
                        item.setSellerId(goods1.getSellerId());
                        TbSeller seller = sellerMapper.selectByPrimaryKey(goods1.getSellerId());
                        item.setSeller(seller.getNickName());
                        //3.4设置brand品牌
                        Long brandId = goods1.getBrandId();
                        TbBrand tbBrand = brandMapper.selectByPrimaryKey(brandId);
                        item.setBrand(tbBrand.getName());
                        //3.5设置分类，三级分类，通过上面查询出的三级分类设置
                        item.setCategory(tbItemCat.getName());
                        //3.6设置三级分类的id
                        item.setCategoryid(goods1.getCategory3Id());
                        //3.7设置创建时间和更新时间
                        item.setCreateTime(new Date());
                        //3.8设置更新时间
                        item.setUpdateTime(new Date());
                        //3.9设置图片,通过goodsDesc中的imageItems获取第一张图片
                        String itemImages = goodsDesc.getItemImages();
                        List<Map> maps = JSON.parseArray(itemImages, Map.class);
                        Map map = maps.get(0);
                        item.setImage((String) map.get("url"));
                        //3.10设置title，设置为goodsName
                        item.setTitle(goods1.getGoodsName());
                        //3.10设置完成，将其添加到tb-items中
                        itemMapper.insert(item);
                    }
                }
                resoult.setMessage("添加成功!");
                resoult.setItems(insert);
            }
        }
        return resoult;
    }

    @Override
    public PageResoult<TbGoods> findAll() {
        List<TbGoods> tbGoods = goodsMapper.selectByExample(null);
        PageResoult<TbGoods> resoult = new PageResoult<>();
        resoult.setRows(tbGoods);
        return resoult;
    }

    @Override
    public PageResoult<TbGoods> searchByCondition(int currentPage, int pageSize, TbGoods goods) {
        TbGoodsExample example = new TbGoodsExample();
        TbGoodsExample.Criteria criteria = example.createCriteria();
        if (null != goods){
            //设置商家名
            if (goods.getSellerId()!=null && !goods.getSellerId().equals("")){
                criteria.andSellerIdEqualTo(goods.getSellerId());
            }
            //设置商品名
            if (goods.getGoodsName()!=null && !goods.getGoodsName().equals("")){
                criteria.andGoodsNameLike("%" + goods.getGoodsName() + "%");
            }
            //设置状态
            if (goods.getAuditStatus()!=null && !goods.getAuditStatus().equals("")){
                criteria.andAuditStatusEqualTo(goods.getAuditStatus());
            }
            //设置是否删除
            if (goods.getIsDelete()!=null && !goods.getIsDelete().equals("")){
                criteria.andIsDeleteEqualTo(goods.getIsDelete());
            }
            //是否上架
            if (goods.getIsMarketable()!=null && !goods.getIsMarketable().equals("")){
                criteria.andIsMarketableEqualTo(goods.getIsMarketable());
            }
            PageHelper.startPage(currentPage,pageSize);//开始分页
            Page<TbGoods> goodsPage = (Page<TbGoods>) goodsMapper.selectByExample(example);
            return new PageResoult<>(goodsPage.getTotal(),goodsPage.getResult());
        }
        return null;
    }

    @Override
    public Resoult deleteGoods(long[] items) {
        Resoult resoult = new Resoult();
        int count=0;
        resoult.setMessage("删除失败!");
        resoult.setCome(0);
        resoult.setItems(count);
        if (null!=items){
            for (long item : items) {
                //1.根据主键id删除tbGoods表记录
                int i = goodsMapper.deleteByPrimaryKey(item);
                //2.根据主键id删除tbGoodsDesc表记录
                int i1 = goodsDescMapper.deleteByPrimaryKey(item);
                //3.通过外键id，删除tbItem表记录
                TbItemExample example = new TbItemExample();
                TbItemExample.Criteria criteria = example.createCriteria();
                criteria.andGoodsIdEqualTo(item);
                int i2 = itemMapper.deleteByExample(example);
                if (i>0 && i1>0 && i2>0){
                    count++;
                }
            }
            resoult.setMessage("提交成功!");
            resoult.setItems(count);
            resoult.setCome(1);
        }
        return resoult;
    }

    @Override
    public Resoult review(String auditStatus, long[] items) {
        Resoult resoult = new Resoult();
        int count=0;
        resoult.setMessage("提交失败!");
        resoult.setCome(0);
        resoult.setItems(count);
        if (null != items){
            for (long item : items) {
                TbGoods tbGoods = goodsMapper.selectByPrimaryKey(item);//通过id拿到goods信息
                tbGoods.setAuditStatus(auditStatus);//设置该goods的AuditStatus值,未提交为0，提交待审核为1，审核通过为2，驳回为3
                int i = goodsMapper.updateByPrimaryKey(tbGoods);//更新记录
                if (i>0){
                    count++;
                }
            }
            resoult.setMessage("提交成功!");
            resoult.setItems(count);
            resoult.setCome(1);
        }
        return resoult;
    }

    @Override
    public Goods findOne(long id) {
        Goods goods = new Goods();
        TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);//查询tb_goods表
        goods.setGoods(tbGoods);
        TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(id);//查询tb-goodsDesc表，并设置
        goods.setGoodsDesc(tbGoodsDesc);
        TbItemExample example = new TbItemExample();   //根据id查询tbItem表，并设置
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(id);
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        goods.setItems(tbItems);
        return goods;//返回数据
    }

    //运营商删除商品
    @Override
    public Resoult deleteGoodsByManager(long[] items) {
        Resoult resoult = new Resoult("删除失败！");
        resoult.setItems(0);
        int count = 0;
        if (items != null){
            for (long item : items) {  //根据每条id，设置tb-goods表中is_delete字段的属性为1，表示删除
                TbGoods tbGoods = goodsMapper.selectByPrimaryKey(item);
                tbGoods.setIsDelete("1");//设置为1，逻辑被删除
                int i = goodsMapper.updateByPrimaryKey(tbGoods);
                if (i>0){
                    count++;
                }
            }
            resoult.setMessage("删除成功!");
            resoult.setItems(count);
        }
        return resoult;
    }

    @Override
    public Resoult superMarket(long id, String market) {
        //1.根据商品的id找到商品
        TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
        //2.设置tb-goods表中的is_marketable字段
        tbGoods.setIsMarketable(market);
        //3.更新tb-goods表
        int i = goodsMapper.updateByPrimaryKey(tbGoods);
        if (i>0 && market.equals("1")){
            return new Resoult("上架成功！");
        }
        if (i>0 && market.equals("0")){
            return new Resoult("下架成功!");
        }
        return new Resoult("操作失败!");
    }
}
