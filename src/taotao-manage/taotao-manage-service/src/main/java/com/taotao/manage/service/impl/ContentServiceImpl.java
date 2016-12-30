package com.taotao.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.mapper.ContentMapper;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.BaseService;
import com.taotao.manage.service.ContentService;

@Service
public class ContentServiceImpl extends BaseService<Content> implements ContentService {

    @Autowired
    private ContentMapper contentMapper;
    
    @Override
    public Mapper<Content> getMapper() {
        return this.contentMapper;
    }

    @Override
    public Integer saveContent(Content content) {
        content.setId(null);
        return super.save(content);
    }

    @Override
    public PageInfo<Content> queryContentList(Long categoryId, Integer page, Integer rows) {
       
        Content record = new Content();
        record.setCategoryId(categoryId);
        Example example = new Example(Content.class);
        example.createCriteria().andEqualTo("categoryId", categoryId);
        example.setOrderByClause("updated DESC");
        PageHelper.startPage(page, rows);
        List<Content> contents = this.contentMapper.selectByExample(example);
        return new PageInfo<Content>(contents);
    }

    @Override
    public Integer updateContent(Content content) {
        return super.updateSelective(content);
    }

    @Override
    public Integer deleteContentByIds(String ids) {
        String [] id = ids.split(",");
        List<Object> idList = new ArrayList<Object>();
        for(int i=0;i<id.length;i++){
            idList.add(id[i]);
        }
        return super.deleteByIds(Content.class, "id", idList);
    }

}
