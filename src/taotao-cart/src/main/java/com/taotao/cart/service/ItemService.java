package com.taotao.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.bean.Item;
import com.taotao.common.service.APIService;

@Service
public class ItemService {

    @Autowired
    private APIService apiService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Item queryItemById(Long itemId) {
        try {
            String url = "http://manage.taotao.com/rest/item/" + itemId;
            String jsonData = this.apiService.doGet(url);
            if (jsonData != null) {
                return MAPPER.readValue(jsonData, Item.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
