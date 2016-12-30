package com.taotao.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.mapper.ItemDescMapper;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.BaseService;
import com.taotao.manage.service.ItemDescService;

@Service
public class ItemDescServiceImpl extends BaseService<ItemDesc> implements ItemDescService {

    @Autowired
    private ItemDescMapper itemDescMapper;
    @Override
    public Mapper<ItemDesc> getMapper() {
        // TODO Auto-generated method stub
        return this.itemDescMapper;
    }

    @Override
    public Integer saveItemDesc(ItemDesc itemDesc) {
        // TODO Auto-generated method stub
        return this.save(itemDesc);
    }

    @Override
    public ItemDesc queryItemDescById(Long itemId) {
        // TODO Auto-generated method stub
        return super.queryById(itemId);
    }

    @Override
    public Integer updateItemDesc(ItemDesc itemDesc) {
        // TODO Auto-generated method stub
        return super.updateSelective(itemDesc);
    }

}
