package com.taotao.web.service;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.common.service.APIService;
import com.taotao.common.service.RedisService;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.web.bean.Item;

@Service
public class ItemService {

    @Autowired
    private APIService apiService;
    
    @Autowired
    private RedisService redisService;
    
    @Autowired
    private PropertiesService propertiesService;
    
    
    
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Item queryItemById(Long itemId) {
        String key = propertiesService.REDIS_BASE_KEY+itemId;
        try {
            String cacheData = this.redisService.get(key);
            if(StringUtils.isNotEmpty(cacheData)){
                return MAPPER.readValue(cacheData, Item.class);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
        try {
            String url = propertiesService.TAOTAO_MANAGE_URL + propertiesService.QUERY_ITEM_PATH + itemId;
            String jsonData = this.apiService.doGet(url);
            if(StringUtils.isEmpty(jsonData)){
                return null;
            }
            
            try {
                this.redisService.set(key, jsonData, propertiesService.REDIS_ITEM_MAXTIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return MAPPER.readValue(jsonData, Item.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public ItemDesc queryItemDescByItemId(Long itemId){
        try {
            String url = propertiesService.TAOTAO_MANAGE_URL+propertiesService.QUERY_ITEMDESC_PATH+itemId;
            String jsonData = this.apiService.doGet(url);
            if(StringUtils.isEmpty(jsonData)){
                return null;
            }
            return MAPPER.readValue(jsonData, ItemDesc.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String queryItemParamByItemId(Long itemId) {
        try {
            String url = propertiesService.TAOTAO_MANAGE_URL + propertiesService.QUERY_ITEMPARAM_PATH + itemId;
            String jsonData = this.apiService.doGet(url);
            if(StringUtils.isEmpty(jsonData)){
                return null;
            }
            // 解析JSON
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            ArrayNode paramData = (ArrayNode) MAPPER.readTree(jsonNode.get("paramData").asText());
            StringBuilder sb = new StringBuilder();
            sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\"><tbody>");
            for (JsonNode param : paramData) {
                sb.append("<tr><th class=\"tdTitle\" colspan=\"2\">" + param.get("group").asText()
                        + "</th></tr>");
                ArrayNode params = (ArrayNode) param.get("params");
                for (JsonNode p : params) {
                    sb.append("<tr><td class=\"tdTitle\">" + p.get("k").asText() + "</td><td>"
                            + p.get("v").asText() + "</td></tr>");
                }
            }
            sb.append("</tbody></table>");
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
