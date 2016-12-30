package com.taotao.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.taotao.manage.mapper.ItemParamMapper;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.BaseService;
import com.taotao.manage.service.ItemCatService;
import com.taotao.manage.service.ItemParamService;
import com.taotao.manage.view.ItemParamView;

@Service
public class ItemParamServiceImpl extends BaseService<ItemParam> implements ItemParamService {

    @Autowired
    private ItemParamMapper itemParamMapper;

    @Autowired
    private ItemCatService itemCatService;

    @Override
    public Mapper<ItemParam> getMapper() {
        // TODO Auto-generated method stub
        return this.itemParamMapper;
    }

    @Override
    public ItemParam queryItemParamByItemCatId(Long itemCatId) {
        ItemParam itemParam = new ItemParam();
        itemParam.setItemCatId(itemCatId);
        return super.queryOne(itemParam);
    }

    @Override
    public Integer saveParamItem(ItemParam itemParam) {
        // TODO Auto-generated method stub
        return super.save(itemParam);
    }

    @Override
    public List<ItemParamView> queryAllItems(Integer page, Integer rows) {
        Example example = new Example(ItemParam.class);
        example.setOrderByClause("updated DESC");
        // 使用分页助手
        /*
         * 查询商品的规格参数,结果发现itemParamList集合中并没有值， 建议能不能把pageInfo对象和startPage方法分开
         * 不论使不使用PageInfo对象，都可以让下面的这条代码返回我们想要的分页结果集
         */
        PageHelper.startPage(page, rows);
        List<ItemParam> itemParamList =  this.itemParamMapper.selectByExample(example);
        List<ItemParamView> viewList = new ArrayList<ItemParamView>();
        for (ItemParam itemParam : itemParamList) {
            // 根据规格参数中的商品种类Id获取商品对象
            ItemCat itemCat = itemCatService.queryItemCatById(itemParam.getItemCatId());
            ItemParamView itemParamView = new ItemParamView();
            // 把规格参数对象和商品种类名称映射入itemParamView对象中
            itemParamView.setItemCatName(itemCat.getName());
            BeanUtils.copyProperties(itemParam, itemParamView);
            viewList.add(itemParamView);
        }
        return viewList;
    }



}
