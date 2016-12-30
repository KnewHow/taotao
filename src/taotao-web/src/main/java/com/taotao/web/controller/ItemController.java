package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.web.bean.Item;
import com.taotao.web.service.ItemService;

@Controller
@RequestMapping("item")
public class ItemController {
    
    @Value("${TAOTAO_MANAGE_URL}")
    private  String TAOTAO_MANAGE_URL;
    
    @Autowired
    private ItemService itemService;

    /**
     * 根据id获取商品信息
     * @param itemId
     * @return
     */
    @RequestMapping(value="{itemId}",method=RequestMethod.GET)
    public ModelAndView showDetail(@PathVariable("itemId")Long itemId){
        ModelAndView mv = new ModelAndView();
        Item item = this.itemService.queryItemById(itemId);
        ItemDesc itemDesc = this.itemService.queryItemDescByItemId(itemId);
        String itemParam = this.itemService.queryItemParamByItemId(itemId);
        mv.addObject("item", item);
        mv.addObject("itemDesc", itemDesc);
        mv.addObject("itemParam", itemParam);
        mv.setViewName("item");
        return mv;
    }
    
    
    
    
    
    
}
