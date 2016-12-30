package com.taotao.manage.controller;

import java.util.List;

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

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamService;
import com.taotao.manage.view.ItemParamView;

@Controller
@RequestMapping("item/param")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemParamController.class);

    /**
     * 根据商品类目的id，查询参数模板
     * 
     * @param itemCatId
     * @return
     */
    @RequestMapping(value = "{itemCatId}", method = RequestMethod.GET)
    public ResponseEntity<ItemParam> queryItemParamByItemCatId(@PathVariable("itemCatId") Long itemCatId) {

        try {
            ItemParam itemParam = this.itemParamService.queryItemParamByItemCatId(itemCatId);
            if (itemParam == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.ok(itemParam);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
    
    

    /**
     * 新增参数模板
     * 
     * @param itemCatId
     * @param itemParam
     * @return
     */
    @RequestMapping(value = "{itemCatId}", method = RequestMethod.POST)
    public ResponseEntity<Void> saveItemParam(@PathVariable("itemCatId") Long itemCatId, ItemParam itemParam) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("新增规格参数模板，itemParam={}", itemParam);
        }
        try {
            itemParam.setItemCatId(itemCatId);
            this.itemParamService.saveParamItem(itemParam);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("新增规格参数成功，itemParam={}", itemParam);
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("新增规格参数失败，itemParam={}", itemParam);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryAllItems(
            @RequestParam(value="page",defaultValue="1")Integer page,
            @RequestParam(value="rows",defaultValue="30")Integer rows){
        
        try {
            
            List<ItemParamView> itemParamList = this.itemParamService.queryAllItems(page, rows);
            //List<ItemParam> itemParamList = pageInfo.getList();
            if(itemParamList==null || itemParamList.size()==0){
                //资源不存在，返回404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            EasyUIResult easyUIResult = new EasyUIResult(itemParamList.size(), itemParamList);
            return ResponseEntity.ok(easyUIResult);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //服务器错误500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
