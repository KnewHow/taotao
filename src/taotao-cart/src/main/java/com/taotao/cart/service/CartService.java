package com.taotao.cart.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.taotao.cart.bean.Item;
import com.taotao.cart.bean.User;
import com.taotao.cart.mapper.CartMapper;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.threadlocal.UserThreadLocal;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ItemService itemService;

    public void addItemToCart(Long itemId) {
        User user = UserThreadLocal.get();
        Cart record = new Cart();
        record.setUserId(user.getId());
        record.setItemId(itemId);
        Cart cart = this.cartMapper.selectOne(record);
        if (cart == null) {
            Item item = this.itemService.queryItemById(itemId);
            if (item == null) {
                return;
            }
            cart = new Cart();
            cart.setItemId(itemId);
            cart.setUserId(user.getId());
            cart.setCreated(new Date());
            cart.setNum(1);//TODO 默认为1
            cart.setItemPrice(item.getPrice());
            cart.setItemTitle(item.getTitle());
            cart.setUpdated(new Date());
            cart.setItemImage(item.getImages()[0]);
            this.cartMapper.insert(cart);
        } else {
            cart.setUpdated(new Date());
            cart.setNum(cart.getNum() + 1);// TODO
            this.cartMapper.updateByPrimaryKeySelective(cart);
        }

    }

    public List<Cart> queryCartList() {
        User user = UserThreadLocal.get();
        Example example = new Example(Cart.class);
        example.createCriteria().andEqualTo("userId", user.getId());
        example.setOrderByClause("created DESC");
        return this.cartMapper.selectByExample(example);
    }
    
    public List<Cart> queryCartList(Long userId) {
        Example example = new Example(Cart.class);
        example.createCriteria().andEqualTo("userId",userId);
        example.setOrderByClause("created DESC");
        return this.cartMapper.selectByExample(example);
    }

    public void updateNum(Long itemId, Integer num) {
        User user = UserThreadLocal.get();
        Cart record = new Cart();
        record.setNum(num);
        record.setUpdated(new Date());
        
        Example example = new Example(Cart.class);
        example.createCriteria().andEqualTo("itemId", itemId).andEqualTo("userId", user.getId());
        this.cartMapper.updateByExampleSelective(record, example);
    }

    public void deleteItem(Long itemId) {
        Cart record = new Cart();
        record.setItemId(itemId);
        record.setUserId(UserThreadLocal.get().getId());
        this.cartMapper.delete(record);
    }

    
    

}
