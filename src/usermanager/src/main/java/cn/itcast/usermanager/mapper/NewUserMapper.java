package cn.itcast.usermanager.mapper;

import java.util.List;

import com.github.abel533.mapper.Mapper;

import cn.itcast.usermanager.pojo.User;
/**
 * 实现在mybatis-config中配置的mapper接口
 * 泛型是需要操作的对象，
 * 在实体类中需要使用主键来和数据库进行匹配
 * @author Administrator
 *
 */
public interface NewUserMapper extends Mapper<User> {

}
