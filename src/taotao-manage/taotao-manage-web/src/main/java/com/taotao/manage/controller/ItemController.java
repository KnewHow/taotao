package com.taotao.manage.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemService;

@Controller
@RequestMapping("item")
public class ItemController {

    // 日志
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDescService itemDescService;

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item, String desc,@RequestParam("itemParams")String itemParams) {

        // 判断是否允许info执行，不然代码的健壮性不强
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("新增商品，item={},desc={}", item, desc);// 使用占位符
        }
        try {
            // 字段校验错误400
            if (item.getTitle().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            // 设置id，防止有人恶意的添加ID
            item.setId(null);
            item.setStatus(1);
            this.itemService.saveItem(item, desc,itemParams);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("新增商品成功，item={},desc={}", item, desc);
            }
            // 成功201
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // 服务器问题500
            LOGGER.error("新增商品失败，item={},desc={}", item, desc);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryItemList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "30") Integer rows) {

        try {
            PageInfo<Item> pageInfo = this.itemService.queryItemList(page, rows);
            //List<Item> list = pageInfo.getList();
            EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
            return ResponseEntity.ok(easyUIResult);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(value = "typeName/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<ItemCat> queryTypeNameById(@PathVariable("itemId") Long itemId) {

        try {
            Item item = this.itemService.queryItemById(itemId);
            if (item == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                ItemCat itemCat = this.itemCatService.queryItemCatById(item.getCid());
                if (itemCat == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                } else {
                    return ResponseEntity.ok(itemCat);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
    
    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<Void> updateItem(Item item,String desc,@RequestParam("itemParams")String itemParams ){
        if(LOGGER.isInfoEnabled()){
            LOGGER.info("修改商品，item={},desc={}",item,desc);
        }
        try {
            // 字段校验错误400
            if (item.getTitle().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            //修改成功204
            this.itemService.updateItem(item, desc,itemParams);
            if(LOGGER.isInfoEnabled()){
                LOGGER.info("修改商品成功，item={},desc={}",item,desc);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("修改商品失败item={},desc={}",item,desc);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @RequestMapping(value="{itemId}",method=RequestMethod.GET)
    public ResponseEntity<Item> queryItemById(@PathVariable("itemId")Long itemId){
        try {
            Item item = this.itemService.queryItemById(itemId);
            if(item==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }else{
                return ResponseEntity.ok(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
