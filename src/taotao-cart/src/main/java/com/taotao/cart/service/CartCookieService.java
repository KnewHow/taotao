package com.taotao.cart.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.bean.Item;
import com.taotao.cart.pojo.Cart;
import com.taotao.commom.utils.CookieUtils;

@Service
public class CartCookieService {

    private static final String COOKIE_NAME = "TT_CART";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    private static final Integer  COOKIE_MAXAGE = 60*60*24*30*12;

    @Autowired
    private ItemService itemService;

    public void addItemToCart(Long itemId, HttpServletRequest request, HttpServletResponse response) {
        List<Cart> carts = queryCartList(request);
        // 判断该商品在购物车中是否存在
        Cart cart = null;
        for (Cart c : carts) {
            if (c.getItemId().longValue() == itemId.longValue()) {
                cart = c;
                break;
            }
        }

        // 不存在
        if (cart == null) {
            Item item = this.itemService.queryItemById(itemId);
            if (item == null) {
                return;
            }
            cart = new Cart();
            cart.setItemId(itemId);
            cart.setCreated(new Date());
            cart.setNum(1);// TODO 默认为1
            cart.setItemPrice(item.getPrice());
            cart.setItemTitle(item.getTitle());
            cart.setUpdated(new Date());
            cart.setItemImage(item.getImages()[0]);
            carts.add(cart);
        } else {
            cart.setUpdated(new Date());
            cart.setNum(cart.getNum() + 1);// TODO
        }
        
        writeToCookie(carts, request, response);
    }

    public List<Cart> queryCartList(HttpServletRequest request) {
        String cookieValue = CookieUtils.getCookieValue(request, COOKIE_NAME, true);
        List<Cart> carts = null;
        if (StringUtils.isEmpty(cookieValue)) {
            carts = new ArrayList<Cart>();
        } else {
            try {
                carts = MAPPER.readValue(cookieValue,
                        MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
            } catch (Exception e) {
                e.printStackTrace();
                carts = new ArrayList<Cart>();
            }
        }
        return carts;
    }

    public void updateNum(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        List<Cart> carts = queryCartList(request);
        // 判断该商品在购物车中是否存在
        for (Cart c : carts) {
            if (c.getItemId().longValue() == itemId.longValue()) {
                c.setNum(num);
                c.setUpdated(new Date());
                break;
            }
        }
        writeToCookie(carts, request, response);
    }
    
    
    
    private void writeToCookie(List<Cart> carts,HttpServletRequest request,HttpServletResponse response){
        try {
            CookieUtils.setCookie(request, response, COOKIE_NAME, MAPPER.writeValueAsString(carts),COOKIE_MAXAGE , true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(Long itemId, HttpServletRequest request, HttpServletResponse response) {
        List<Cart> carts = queryCartList(request);
        // 判断该商品在购物车中是否存在
        for (Cart c : carts) {
            if (c.getItemId().longValue() == itemId.longValue()) {
                carts.remove(c);
                break;
            }
        }
        writeToCookie(carts, request, response);
    }
    

}
