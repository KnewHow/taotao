package com.taotao.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.web.bean.TaoTaoNews;
import com.taotao.web.service.IndexService;

@Controller
public class IndexController {
    
    @Autowired
    private IndexService indexService;

    @RequestMapping(value="index",method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        String indexAd1 =  indexService.queryAd1();
        String indexAd2 =  indexService.queryAd2();
        List<TaoTaoNews> taoTaoNews = this.indexService.queryTaoTaoNews();
        mv.addObject("indexAd1",indexAd1);
        mv.addObject("indexAd2",indexAd2);
        mv.addObject("taoTaoNews",taoTaoNews);
        return mv;
    }
    
    
}
