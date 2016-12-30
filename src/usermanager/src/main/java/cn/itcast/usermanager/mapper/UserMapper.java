package cn.itcast.usermanager.mapper;

import java.util.List;

import cn.itcast.usermanager.pojo.User;

public interface UserMapper {

    public List<User> queryUserList();
}
