package com.taotao.manage.service;

import com.taotao.manage.pojo.ItemParamItem;

public interface ItemParamItemService {
    
    public Integer saveItemParamItem(ItemParamItem itemParamItem); 
    
    public ItemParamItem queryByItemId(Long itemId);
    
    public Integer updateByItemId(Long itemId,String itemParams);

}
