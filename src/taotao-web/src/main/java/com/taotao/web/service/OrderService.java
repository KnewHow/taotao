package com.taotao.web.service;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.httpclient.HttpResult;
import com.taotao.common.service.APIService;
import com.taotao.web.bean.Order;
import com.taotao.web.bean.User;
import com.taotao.web.threadlocal.UserThreadLocal;

@Service
public class OrderService {

    @Autowired
    private APIService apiService;
    
    @Autowired
    private PropertiesService propertiesService;

    private static final ObjectMapper MAPPER = new ObjectMapper();


    /**
     * 创建订单：返回创建订单的订单号
     * 
     * @param order
     * @return
     */
    public String creatOrder(Order order) {
        
        //获取当前线程中的user对象
        User user = UserThreadLocal.get();
        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());
        String url = propertiesService.TAOTAO_ORDER_URL+propertiesService.CREATE_ORDER;
        try {
            HttpResult httpResult = this.apiService.doPostJson(url, MAPPER.writeValueAsString(order));
            if (httpResult.getStatusCode() == 200) {
                String jsonData = httpResult.getData();
                JsonNode jsonNode = MAPPER.readTree(jsonData);
                if (jsonNode.get("status").intValue() == 200) {
                    return jsonNode.get("data").asText();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 根据id查询订单
     * @param orderId
     * @return
     */
    public Order queryOrderById(String orderId){
        
        String url = propertiesService.TAOTAO_ORDER_URL+propertiesService.QUERY_ORDER+orderId;
        try {
            String jsonData = this.apiService.doGet(url);
            if(StringUtils.isNotEmpty(jsonData)){
               return  MAPPER.readValue(jsonData, Order.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
