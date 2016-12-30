package com.taotao.web.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.APIService;
import com.taotao.web.bean.Cart;

@Service
public class CartService {

    @Autowired
    private APIService apiService;

    @Autowired
    private PropertiesService propertiesService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public List<Cart> queryCartsByUser(Long userId) {
        try {
            String url = propertiesService.TAOTAO_CART_URL + "/service/cart?userId=" + userId;
            String jsonData = apiService.doGet(url);
            if(StringUtils.isEmpty(jsonData)){
                return null;
            }
            return MAPPER.readValue(jsonData, MAPPER.getTypeFactory()
                    .constructCollectionType(List.class, Cart.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
