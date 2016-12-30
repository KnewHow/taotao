package com.taotao.sso.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.service.RedisService;
import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private RedisService redisService;
    
    public static final ObjectMapper MAPPER = new ObjectMapper(); 
    
    public static final String BASE_TOKEN = "TOKEN_";

    @Override
    public Boolean check(String param, Integer type) {
        if (type < 1 || type > 3 ||  StringUtils.isEmpty(param)) {
            return null;
        }
        User record = new User();
        switch (type) {
        case 1:
            record.setUsername(param);
            break;
            
        case 2:
            record.setPhone(param);
            break;
            
        case 3:
            record.setEmail(param);
            break;
        }
        return this.userMapper.selectOne(record)==null;
    }

    @Override
    public Boolean doRegister(User user) {
        user.setId(null);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //对密码进行加密
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        return this.userMapper.insert(user)==1;
    }

    @Override
    public String doLogin(String username, String password) throws Exception {
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);
        if(user==null){
            return null;
        }
        if(!StringUtils.equals(DigestUtils.md5Hex(password), user.getPassword())){
            return null;
        }
        //生成token
        String token = DigestUtils.md5Hex(System.currentTimeMillis()+username);
        
        //这里的redis是作为数据库存储，所以要对异常进行抛出
        redisService.set(BASE_TOKEN+token, MAPPER.writeValueAsString(user), 60*30);
        return token;
    }

    @Override
    public User queryUserByToken(String token) {
        String key = BASE_TOKEN+token;
        String jsonData = this.redisService.get(key);
        if(StringUtils.isEmpty(jsonData)){
            return null;
        }else{
            try {
                //当用户在活跃的时候，需要刷新用户的生存时间
                this.redisService.expire(key, 60*30);
                return MAPPER.readValue(jsonData, User.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void logout(String token) {
        String key = BASE_TOKEN+token;
        this.redisService.del(key);
    }
}
