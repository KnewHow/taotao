package com.taotao.manage.service;

import java.util.List;

import com.taotao.common.bean.ItemCatResult;
import com.taotao.manage.pojo.ItemCat;

public interface ItemCatService {

    public List<ItemCat> queryItemCatListByParent(Long parentId);
    
    public ItemCat queryItemCatById(Long itemCatId);
    
    public ItemCatResult queryAllToTree();
        
}
