package com.taotao.manage.service;

import java.util.List;

import com.taotao.manage.pojo.ContentCategory;

public interface CopyOfContentCategoryService {
    
    public List<ContentCategory> queryContentCategoryList(Long parentId);

}
