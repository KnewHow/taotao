package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;

@Controller
@RequestMapping("/item/desc")
public class ItemDescController {
    
    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(value="{itemId}",method=RequestMethod.GET)
    public ResponseEntity<ItemDesc> getItemDescById(@PathVariable("itemId")Long itemId){
        
        try {
            ItemDesc itemDesc =  this.itemDescService.queryItemDescById(itemId);
            if(itemDesc==null){
                //资源不存在，400
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }else{
                return ResponseEntity.ok(itemDesc);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //服务器抛出异常 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }
}
