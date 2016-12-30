package com.taotao.manage.service;

import java.util.List;

import com.taotao.manage.pojo.ContentCategory;

public interface ContentCategoryService {
    
    public List<ContentCategory> queryContentCategoryList(Long parentId);

    public ContentCategory saveContextCategory(ContentCategory contentCategory);
    
    public Integer renameContextCategory(ContentCategory contentCategor);
    
    public Integer deleteContextCategory(ContentCategory contentCategor);
}
