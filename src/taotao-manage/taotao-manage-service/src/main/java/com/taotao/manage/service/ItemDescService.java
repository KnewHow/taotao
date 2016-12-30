package com.taotao.manage.service;

import com.taotao.manage.pojo.ItemDesc;

public interface ItemDescService {

    public Integer saveItemDesc(ItemDesc itemDesc); 
    
    public ItemDesc queryItemDescById(Long itemId);
    
    public Integer updateItemDesc(ItemDesc itemDesc);
}
