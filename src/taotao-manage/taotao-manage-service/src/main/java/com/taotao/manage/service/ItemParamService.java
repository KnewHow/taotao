package com.taotao.manage.service;

import java.util.List;

import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.view.ItemParamView;

public interface ItemParamService {

    public ItemParam queryItemParamByItemCatId(Long itemCatId);
    
    public Integer saveParamItem(ItemParam itemParam);
 
    public List<ItemParamView> queryAllItems(Integer page, Integer rows);
}
