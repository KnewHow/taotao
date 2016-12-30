package com.taotao.manage.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.taotao.manage.mapper.ItemParamItemMapper;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.manage.service.BaseService;
import com.taotao.manage.service.ItemParamItemService;

@Service
public class ItemParamItemServiceImpl extends BaseService<ItemParamItem> implements ItemParamItemService {

    @Autowired
    private ItemParamItemMapper itemParamItemMapper;
    
    @Override
    public Mapper<ItemParamItem> getMapper() {
        return this.itemParamItemMapper;
    }

    @Override
    public Integer saveItemParamItem(ItemParamItem itemParamItem) {
        return super.save(itemParamItem);
    }

    @Override
    public ItemParamItem queryByItemId(Long itemId) {
        ItemParamItem record = new ItemParamItem();
        record.setItemId(itemId);
        return super.queryOne(record);
    }

    @Override
    public Integer updateByItemId(Long itemId, String itemParams) {
        ItemParamItem record = new ItemParamItem();
        record.setParamData(itemParams);
        record.setCreated(new Date());
        
        Example example = new Example(ItemParamItem.class);
        example.createCriteria().andEqualTo("itemId", itemId);
        return this.itemParamItemMapper.updateByExampleSelective(record, example);
    }

}
