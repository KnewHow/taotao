package com.taotao.sso.service;


import com.taotao.sso.pojo.User;

public interface UserService {

    public Boolean check(String param,Integer type);
    public Boolean doRegister(User user);
    
    public String doLogin(String username,String password)throws Exception;
    
    public User queryUserByToken(String token);
    
    public void logout(String token);
}
