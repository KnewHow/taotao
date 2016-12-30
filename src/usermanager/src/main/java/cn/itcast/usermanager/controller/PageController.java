package cn.itcast.usermanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 跳转到页面
 * @author Administrator
 *
 */
@RequestMapping("page")
@Controller
public class PageController {

    @RequestMapping(value="{pageName}",method=RequestMethod.GET)
    public String toPage(@PathVariable("pageName") String pageName){
        return pageName;
    }

}
