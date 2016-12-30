package com.taotao.cart.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService {


    // 后台总入口路径
    @Value("${TAOTAO_MANAGE_URL}")
    public String TAOTAO_MANAGE_URL;

    // 查询商信息路径
    @Value("${QUERY_ITEM_PATH}")
    public String QUERY_ITEM_PATH;

    // 查询商品描述信息路径
    @Value("${QUERY_ITEMDESC_PATH}")
    public String QUERY_ITEMDESC_PATH;

    // 查询商品规格参数信息路径
    @Value("${QUERY_ITEMPARAM_PATH}")
    public String QUERY_ITEMPARAM_PATH;


    // 订单系统的总入口
    @Value("${TAOTAO_ORDER_URL}")
    public String TAOTAO_ORDER_URL;

    // 创建订单入口
    @Value("${CREATE_ORDER}")
    public String CREATE_ORDER;

    // SSO单点登录的总入口
    @Value("${TAOTAO_SSO_URL}")
    public String TAOTAO_SSO_URL;

    //获取用户信息的总入口
    @Value("${SERVICE_USER}")
    public String SERVICE_USER;
    
    
    //用户登录界面
    @Value("${USER_LOGIN}")
    public String USER_LOGIN;
    
    //查询订单
    @Value("${QUERY_ORDER}")
    public String QUERY_ORDER;
    
    
}
