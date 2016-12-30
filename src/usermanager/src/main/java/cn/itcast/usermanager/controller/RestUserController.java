package cn.itcast.usermanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.itcast.usermanager.pojo.User;
import cn.itcast.usermanager.service.UserService;

/**
 * 使用RESTful标准来开发
 * @author Administrator
 *
 */
@Controller
@RequestMapping("restful/user")
public class RestUserController {
    
    @Autowired
    private UserService userService;

    @RequestMapping(value="{id}",method=RequestMethod.GET)
    public ResponseEntity<User> queryUserById(@PathVariable("id")Long id){
        try {
            User user = this.userService.queryUserById(id);
            if(user==null){//如果资源没有找到，设置状态码为404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }else{
                //return ResponseEntity.status(HttpStatus.OK).body(user);
                return ResponseEntity.ok(user);//上面的代码可以改写成这个
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    /**
     * 使用RESTful标准添加用户
     * @param user
     * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> saveUser(User user){
        try {
            this.userService.saveUser(user);
            //return ResponseEntity.status(HttpStatus.CREATED).body(null);
            //上面的代码等价于下面的代码
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {//如果报错，直接抛出500
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * 使用RESTful标准修改用户
     * 对应PUT和DELETE的请求，默认情况下，PUT和DELETE请求是无法提交表单数据的。
                        解决方案：在web.xml中配置Spring提供的过滤器解决
     * @param user
     * @return
     */
    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(User user){
        try {
            this.userService.updateUser(user);
            //返回204的状态码
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * 使用RESTful标准删除用户
     * @param id
     * @return
     */
    @RequestMapping(method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUserById(@RequestParam(defaultValue="0") Long id){
        try {
            if(id==0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            this.userService.deleteUserById(id);
            //返回204的状态码
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
