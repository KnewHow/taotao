package com.taotao.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.manage.service.ItemParamItemService;

@Controller
@RequestMapping("item/param/item")
public class ItemParamItemController {
    
    @Autowired
    private ItemParamItemService itemParamItemService;

    @RequestMapping(value="{itemId}",method=RequestMethod.GET)
    public ResponseEntity<ItemParamItem> queryByItemId(
           @PathVariable(value="itemId")Long itemId){
        try {
            ItemParamItem itemParamItem = this.itemParamItemService.queryByItemId(itemId);
            if(itemParamItem==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }else{
                return ResponseEntity.ok(itemParamItem);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
