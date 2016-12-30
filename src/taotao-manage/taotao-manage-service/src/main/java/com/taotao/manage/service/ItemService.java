package com.taotao.manage.service;


import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.Item;

public interface ItemService {

    public Integer saveItem(Item item,String desc,String itemParams);
    
    public PageInfo<Item> queryItemList(Integer page,Integer rows);
    
    public Item queryItemById(Long id);
    
    public Integer updateItem(Item item,String desc,String itemParams);
    
   
    
}
