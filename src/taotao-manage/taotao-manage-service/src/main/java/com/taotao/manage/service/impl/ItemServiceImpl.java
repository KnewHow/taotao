package com.taotao.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.service.APIService;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.manage.service.BaseService;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemParamItemService;
import com.taotao.manage.service.ItemService;
import com.taotao.manage.service.PropertiesService;

@Service
public class ItemServiceImpl extends BaseService<Item> implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescService itemDescService;
    
    @Autowired
    private APIService apiService;
    
    @Autowired
    private ItemParamItemService itemParamItemService;
    
    @Autowired
    private PropertiesService propertiesService;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    
    @Override
    public Integer saveItem(Item item,String desc,String itemParams) {
        int count = this.save(item);
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemDesc(desc);
        itemDesc.setItemId(item.getId());
        //使用事务的传播特性，可以对这里面的service进行事务管理
        itemDescService.saveItemDesc(itemDesc);
        
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setParamData(itemParams);
        itemParamItem.setItemId(item.getId());
        this.itemParamItemService.saveItemParamItem(itemParamItem);
        sendMSg(item.getId(), "insert");
        return count;
    }

    @Override
    public Mapper<Item> getMapper() {
        return this.itemMapper;
    }

    @Override
    public PageInfo<Item> queryItemList(Integer page, Integer rows) {
        Example example = new Example(Item.class);
        example.setOrderByClause("updated DESC");
        
        //设置分页参数
        PageHelper.startPage(page, rows);
        List<Item> itemList =  this.itemMapper.selectByExample(example);
        return new PageInfo<Item>(itemList);
    }

    @Override
    public Item queryItemById(Long id) {
        return super.queryById(id);
    }

    @Override
    public Integer updateItem(Item item, String desc,String itemParams) {
        //设置不能被修改的参数为null
        item.setCreated(null);
        item.setStatus(null);
        int count =  super.updateSelective(item);
        
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        this.itemDescService.updateItemDesc(itemDesc);
        
        this.itemParamItemService.updateByItemId(item.getId(), itemParams);
        
//        String url = propertiesService.TAOTAO_WEB_URL+propertiesService.UPDATE_ITEM_INFO_URL+item.getId()+".html";
//        
//        try {
//            this.apiService.doPost(url, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
        sendMSg(item.getId(), "update");
        return count;
        
    }
    
    
    private void sendMSg(Long itemId,String type){
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("itemId", itemId);
            map.put("type", type);
            map.put("date", System.currentTimeMillis());
            this.rabbitTemplate.convertAndSend("item."+type, MAPPER.writeValueAsString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }



}
