package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.service.ContentService;

@Controller
@RequestMapping("content")
public class ContentController {

    @Autowired
    private ContentService contentService;
    
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> saveContent(Content content){
        try {
            this.contentService.saveContent(content);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryContent(@RequestParam(value="categoryId") Long categoryId,
           @RequestParam(value="page") Integer page,
           @RequestParam(value="rows") Integer rows){
        
        try {
            PageInfo<Content> pageInfo = this.contentService.queryContentList(categoryId, page, rows);
            EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
            return ResponseEntity.ok(easyUIResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<Void> editContent(Content content){
        try {
            this.contentService.updateContent(content);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @RequestMapping(method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteContentByIds(@RequestParam(value="ids")String ids){
        try {
            this.contentService.deleteContentByIds(ids);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
