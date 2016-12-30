package com.taotao.manage.service;

import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.Content;

public interface ContentService {

    public Integer saveContent(Content content);
    
    public PageInfo<Content> queryContentList(Long categoryId,Integer page,Integer rows);
    
    public Integer updateContent(Content content);
    
    public Integer deleteContentByIds(String ids);
}
