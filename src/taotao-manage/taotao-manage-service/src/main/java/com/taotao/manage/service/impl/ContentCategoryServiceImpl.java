package com.taotao.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.mapper.ContentCategoryMapper;
import com.taotao.manage.pojo.ContentCategory;
import com.taotao.manage.service.BaseService;
import com.taotao.manage.service.ContentCategoryService;
@Service
public class ContentCategoryServiceImpl extends BaseService<ContentCategory> implements ContentCategoryService {

    @Autowired
    private ContentCategoryMapper contentCategoryMapper;
    @Override
    public Mapper<ContentCategory> getMapper() {
        return this.contentCategoryMapper;
    }
    
    @Override
    public List<ContentCategory> queryContentCategoryList(Long parentId) {
        ContentCategory contentCategory = new ContentCategory();
        contentCategory.setParentId(parentId);
        return super.queryListByWhere(contentCategory);
    }

    @Override
    public ContentCategory saveContextCategory(ContentCategory contentCategory) {
        contentCategory.setIsParent(false);
        contentCategory.setSortOrder(1);
        contentCategory.setStatus(1);
        contentCategory.setId(null);
        super.save(contentCategory);
        
        //获取父节点
        ContentCategory parent = super.queryById(contentCategory.getParentId());
        if(!parent.getIsParent()){//判断是否为父节点
            parent.setIsParent(true);
            super.updateSelective(parent);
        }
        return contentCategory;
    }

    @Override
    public Integer renameContextCategory(ContentCategory contentCategor) {
        return super.updateSelective(contentCategor);
    }

    /**
     * 删除某个节点以及器下面的所有子节点
     * 思路：
     *  1、使用递归的方法查询其下面所有的子节点
     *  2、判断该结点的父节点是否还有其他子节点，如果有，不变，如果没有，就把他的isParent改为false
     */
    @Override
    public Integer deleteContextCategory(ContentCategory contentCategory) {
        List<Object> ids = new ArrayList<Object>();
        Long id = contentCategory.getId();
        ids.add(id);
        //获取所有的子节点id
        this.findAllSubNode(id, ids);
        //根据id批量节点
        int count = super.deleteByIds(ContentCategory.class, "id", ids);
        
        ContentCategory record = new ContentCategory();
        record.setParentId(contentCategory.getParentId());
        //判断其父节点还有木有子节点
        List<ContentCategory> list = super.queryListByWhere(record);
        if(list==null || list.isEmpty()){//如果没有子节点，就把isParent设置为false
            ContentCategory parent = new ContentCategory();
            parent.setId(contentCategory.getParentId());
            parent.setIsParent(false);
            super.updateSelective(parent);
        }
        return count;
    }
    
    private void findAllSubNode(Long parentId,List<Object> ids){
        ContentCategory record = new ContentCategory();
        record.setParentId(parentId);
        List<ContentCategory> list =  super.queryListByWhere(record);
        for (ContentCategory contentCategory : list) {
            ids.add(contentCategory.getId());
            //如果子节点也是父节点，在进行递归
            if(contentCategory.getIsParent()){
                findAllSubNode(contentCategory.getId(),ids); 
            }
        }
    }

}
