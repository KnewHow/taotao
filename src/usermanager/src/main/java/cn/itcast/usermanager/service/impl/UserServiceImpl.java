package cn.itcast.usermanager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.itcast.usermanager.bean.EasyUIResult;
import cn.itcast.usermanager.mapper.UserMapper;
import cn.itcast.usermanager.pojo.User;
import cn.itcast.usermanager.service.UserService;
public class UserServiceImpl {

    @Autowired
    private UserMapper userMapper;
    
    public EasyUIResult queryUserList(Integer page,Integer rows){
        EasyUIResult easyUIResult = new EasyUIResult();
        //使用分页住手
        PageHelper.startPage(page, rows);
        List<User> userList = userMapper.queryUserList();
        PageInfo<User> pageInfo = new PageInfo<User>(userList);
        easyUIResult.setRows(pageInfo.getList());
        easyUIResult.setTotal(pageInfo.getTotal());
        return easyUIResult;
    }
}
