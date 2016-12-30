package cn.itcast.usermanager.service;

import cn.itcast.usermanager.bean.EasyUIResult;
import cn.itcast.usermanager.pojo.User;

public interface UserService {

    public EasyUIResult queryUserList(Integer page,Integer rows);
    
    public User queryUserById(Long id);
    
    public void saveUser(User user);
    
    public void updateUser(User user);
    
    public void deleteUserById(Long id);
}
