package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService {

    @Value("${REPOSITORY_PATH}")
    public String REPOSITORY_PATH;

    @Value("${IMAGE_BASE_URL}")
    public String IMAGE_BASE_URL;

    // 前台访问的根路径
    @Value("${TAOTAO_WEB_URL}")
    public String TAOTAO_WEB_URL;

    // 修改商品需要通知的路径
    @Value("${UPDATE_ITEM_INFO_URL}")
    public String UPDATE_ITEM_INFO_URL;
}
