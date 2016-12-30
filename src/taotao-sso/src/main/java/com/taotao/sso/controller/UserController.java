package com.taotao.sso.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.commom.utils.CookieUtils;
import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    private static final String COOKIE_NAME = "TT_TOKEN";

    @RequestMapping(value="register",method=RequestMethod.GET)
    public String  toRegister(HttpServletRequest request,HttpServletResponse response) throws IOException{
        
        return "register";
    }
    
    
    @RequestMapping(value="login",method=RequestMethod.GET)
    public String toLogin(){
        return "login";
    }
    
    /**
     * 校验参数
     * @param param
     * @param type
     * @return
     */
    @RequestMapping(value="check/{param}/{type}",method=RequestMethod.GET)
    public ResponseEntity<Boolean> check(@PathVariable("param")String param,@PathVariable("type")Integer type){
        try {
            Boolean bool = this.userService.check(param, type);
            if(bool==null){//参数错误
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            //为兼容前端js，做取反操作
            return ResponseEntity.ok(!bool);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    /**
     * 用户注册
     * @return
     */
    @RequestMapping(value="doRegister",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doRegister(@Valid User user,BindingResult bindingResult){
        Map<String, Object> result = new HashMap<String, Object>();
        
        if(bindingResult.hasErrors()){
            List<String> msgs = new ArrayList<String>();
            List<ObjectError> errors =  bindingResult.getAllErrors();
            for(ObjectError error:errors){
                String msg = error.getDefaultMessage();
                msgs.add(msg);
            }
            result.put("status", "400");
            result.put("data", StringUtils.join(msgs, "|"));
            return result;
        }
        
        
        Boolean bool = this.userService.doRegister(user);
        if(bool){
            result.put("status", "200");
        }else{
            result.put("status", "400");
            result.put("data", " ");
        }
        return result;
    }
    
    @RequestMapping(value="doLogin",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(String username,String password,HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String token = this.userService.doLogin(username, password);
            if(token==null){
                //登录失败
                result.put("status", "400");
            }else{
                //登录成功，把token设置到cookie中
                CookieUtils.setCookie(request, response, COOKIE_NAME,token);
                result.put("status", "200");
            }
            
           
            
        } catch (Exception e) {
            result.put("status", "500");
            e.printStackTrace();
        }
        return result;
    }
    
    @RequestMapping(value="{token}",method=RequestMethod.GET)
    public ResponseEntity<User> queryUserByToken(@PathVariable("token")String token){
        try {
            User user = this.userService.queryUserByToken(token);
            if(user==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @RequestMapping(value="logout",method=RequestMethod.GET)
    public String logout(HttpServletRequest  request){
        String token = CookieUtils.getCookieValue(request, COOKIE_NAME);
        this.userService.logout(token);
        return toLogin();
    }
}
