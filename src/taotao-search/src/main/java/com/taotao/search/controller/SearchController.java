package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.search.bean.SearchResult;
import com.taotao.search.service.SearchService;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam("q") String keyWords,
            @RequestParam(value = "page", defaultValue = "1") Integer page) {

        ModelAndView mv = new ModelAndView("search");
        SearchResult searchResult = null;
        try {
            keyWords = new String(keyWords.getBytes("ISO-8859-1"),"UTF-8");
            searchResult = this.searchService.search(keyWords, page);
            mv.addObject("query", keyWords);
            // 搜索结果集
            mv.addObject("itemList", searchResult.getData());
            // 页数
            mv.addObject("page", page);
            Integer total = searchResult.getTotal().intValue();
            Integer pages = total % SearchService.ROWS == 0 ? total / SearchService.ROWS : total
                    / SearchService.ROWS + 1;
            // 总页数
            mv.addObject("pages", pages);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return mv;
    }
}
