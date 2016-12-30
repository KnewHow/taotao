package com.taotao.web.service;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.APIService;
import com.taotao.web.bean.User;

@Service
public class UserService {

    @Autowired
    private APIService apiService;
    
    @Autowired
    private PropertiesService propertiesService;
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    /**
     * 根据token获取user对象
     * @param token
     * @return
     */
    public User queryUserByToken(String token){
        String url = this.propertiesService.TAOTAO_SSO_URL+this.propertiesService.SERVICE_USER+token;
        try {
            String jsonData = this.apiService.doGet(url);
            if(StringUtils.isNoneEmpty(jsonData)){
                return MAPPER.readValue(jsonData, User.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
