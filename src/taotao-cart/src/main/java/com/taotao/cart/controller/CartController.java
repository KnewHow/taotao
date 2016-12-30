package com.taotao.cart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.cart.bean.User;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.service.CartCookieService;
import com.taotao.cart.service.CartService;
import com.taotao.cart.threadlocal.UserThreadLocal;

@Controller
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartCookieService cartCookieService;

    /**
     * 添加商品到购物车
     * 
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public String addItemToCart(@PathVariable("itemId") Long itemId, HttpServletRequest request,
            HttpServletResponse response) {
        User user = UserThreadLocal.get();
        if (user == null) {// 处于未登录状态
            this.cartCookieService.addItemToCart(itemId, request, response);
        } else {// 处于登录状态
            this.cartService.addItemToCart(itemId);
        }
        // 返回购物车界面
        return "redirect:/cart/list.html";
    }
    
    /**
     * 对外提供一个根据uid查询购物车信息的接口
     * @param userId
     * @return
     */
    @RequestMapping(method=RequestMethod.GET,params="userId")
    public ResponseEntity<List<Cart>> queryCartsByUser(@RequestParam("userId")Long userId){
        try {
            List<Cart> carts = this.cartService.queryCartList(userId);
            if(carts==null || carts.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }else{
                return ResponseEntity.ok(carts);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 显示到购物车页面
     * 
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView showCartList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("cart");
        User user = UserThreadLocal.get();
        List<Cart> cartList = null;
        if (user == null) {
            cartList = this.cartCookieService.queryCartList(request);
        } else {
            cartList = this.cartService.queryCartList();
        }

        mv.addObject("cartList", cartList);

        return mv;

    }

    /**
     * 
     * @param itemId
     * @param num 最终购买的总数据
     * @return
     */
    @RequestMapping(value = "update/num/{itemId}/{num}", method = RequestMethod.POST)
    public ResponseEntity<Void> updateNum(@PathVariable("itemId") Long itemId,
            @PathVariable("num") Integer num, HttpServletRequest request, HttpServletResponse response) {
        User user = UserThreadLocal.get();
        if (user == null) {
            this.cartCookieService.updateNum(itemId, num, request, response);
        } else {
            this.cartService.updateNum(itemId, num);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @RequestMapping(value = "/delete/{itemId}", method = RequestMethod.GET)
    public String deleteItem(@PathVariable("itemId") Long itemId, HttpServletRequest request,
            HttpServletResponse response) {
        User user = UserThreadLocal.get();
        if (user == null) {
            this.cartCookieService.deleteItem(itemId,request,response);
        } else {
            this.cartService.deleteItem(itemId);
        }
        // 返回购物车界面
        return "redirect:/cart/list.html";
    }
}
