package cn.itcast.usermanager.mapper;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.abel533.entity.Example;

import cn.itcast.usermanager.pojo.User;

/**
 * 测试通用mapper中的方法
 * 
 * @author Administrator
 *
 */
public class NewUserMapperTest {

    private NewUserMapper newUserMapper = null;

    @Before
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:spring/applicationContext*.xml");
        this.newUserMapper = applicationContext.getBean(NewUserMapper.class);

    }

    /**
     * selectOne()：根据一个已知的对象中的属性作为条件来查询对象，返回的是一个或者0个对象 如果查询的结果是对个对象，会报错，如果条件为空，表示没有条件，就会全部进行查询
     */
    @Test
    public void testSelectOne() {
        User record = new User();
        record.setUserName("zhangsan");
        record.setPassword("123456");
        User user = this.newUserMapper.selectOne(null);
        System.out.println(user);
    }

    /**
     * select():根据一个对象中的属性作为组合查询的条件，返回一个该对象的list集合 如果查询的结果为为null，返回的是一个size=0的list集合
     */
    @Test
    public void testSelect() {
        User record = new User();
        record.setUserName("zhangsan");
        record.setPassword("1234567");
        List<User> userList = this.newUserMapper.select(record);
        System.out.println(userList.size());
        for (User user : userList) {
            System.out.println(user);
        }
    }

    /**
     * selectCount():根据给定对象的属性作为组合查询的条件
     * 返回查询出来的记录数
     */
    @Test
    public void testSelectCount() {
        User record = new User();
        record.setUserName("zhangsan");
        record.setPassword("123456");
        int count = this.newUserMapper.selectCount(null);
        System.out.println(count);
    }
    
    /**
     * selectByPrimaryKey():根据主键来查询对象
     * 此主键指的是pojo里面的@id主键所在的属性，而不是数据库的主键
     */
    @Test
    public void testSelectByPrimaryKey() {
        User user =  this.newUserMapper.selectByPrimaryKey(1L);
        System.out.println(user);
    }

    /**
     * insert():往数据库里面插入一条记录
     * 返回影响的行数
     * 不论对象里面的属性是否为空，都会在sql语句中插入对象
     * 选择所有的字段作为插入语句字段
     */
    @Test
    public void testInsert() {
        User record = new User();
        record.setUserName("test_userName_3");
        //record.setPassword("123456");
        //record.setAge(12);
        record.setBirthday(new Date());
        record.setCreated(new Date());
        //record.setSex(1);
        record.setUpdated(new Date());
        record.setName("测试名称3");
        int count = this.newUserMapper.insert(record);
        System.out.println(count);
        System.out.println(record);
    }

    /**
     * insertSelective():有选择性的插入数据
     * 如果对象中某个属性为null，那么在sql语句中就不会插入该数据
     * 将不为空的字段作为插入语句字段
     */
    @Test
    public void testInsertSelective() {
        User record = new User();
        record.setUserName("test_userName_2");
        //record.setPassword("123456");
        //record.setAge(12);
        record.setBirthday(new Date());
        record.setCreated(new Date());
        //record.setSex(1);
        record.setUpdated(new Date());
        record.setName("测试名称2");
        int count = this.newUserMapper.insertSelective(record);
        System.out.println(count);
        System.out.println(record);
    }

    /**
     * delete():根据提供的条件进行删除，如果是null，是没有条件，默认全部删除
     * 返回影响的行数
     */
    @Test
    public void testDelete() {
        //this.newUserMapper.delete(record);
    }
    
    /**
     * deleteByPrimaryKey():根据主键来进删除
     * 返回影响的行数
     */
    @Test
    public void testDeleteByPrimaryKey() {
       int count = this.newUserMapper.deleteByPrimaryKey(11L);
       System.out.println(count);
    }
    /**
     * 根据主键来更新数据,如果以传入对象的中的属性数据为基准，不论是否为空，都在数据库中进行更新
     * 返回影响的行数
     */
    @Test
    public void testUpdateByPrimaryKey() {
        User record = new User();
        //record.setUserName("test_userName_2");
        //record.setPassword("123456");
        //record.setAge(12);
        //record.setBirthday(new Date());
       // record.setCreated(new Date());
        //record.setSex(1);
        //record.setUpdated(new Date());
        //record.setName("测试名称2");
        record.setId(9L);
        this.newUserMapper.updateByPrimaryKey(record);
    }

    /**
     * 根据主键来有选择性的更新数据，如果对象的数据为null，就不会对数据进行更新
     * 而且除主键以为，需要有其他属性值，不然更新会报错
     * 返回影响的行数
     */
    @Test
    public void testUpdateByPrimaryKeySelective() {
        User record = new User();
        record.setId(12L);
        record.setAge(12);
        int count = this.newUserMapper.updateByPrimaryKeySelective(record);
        System.out.println(count);
    }

    /**
     * 根据条件来查询记录数
     */
    @Test
    public void testSelectCountByExample() {
        Example example = new Example(User.class);
        example.createCriteria().andBetween("id", 5L, 12L);
        int count =  this.newUserMapper.selectCountByExample(example);
        System.out.println(count);
    }
    
    /**
     * 根据条件来删除记录数
     * 返回影响的行数
     */
    @Test
    public void testDeleteByExample() {
        fail("Not yet implemented");
    }

    /**
     * 根据指定的条进行查询，返回一个list集合
     * 可以是in between,equal等复杂的条件
     */
    @Test
    public void testSelectByExample() {
        Example example = new Example(User.class);
        List<Object> values = new ArrayList<Object>();
        values.add(1L);
        values.add(2L);
        values.add(3L);
        //设置查询条件
        example.createCriteria().andIn("id", values);
        List<User> userList =  this.newUserMapper.selectByExample(example);
        for(User user:userList){
            System.out.println(user);
        }
    }

    /**
     * 通过条件来进行选择性的更新
     */
    @Test
    public void testUpdateByExampleSelective() {
        Example example = new Example(User.class);
        User record = new User();
        record.setAge(50);
        List<Object> values = new ArrayList<Object>();
        values.add(8L);
        values.add(12L);
        example.createCriteria().andIn("id", values);
        int count = this.newUserMapper.updateByExampleSelective(record, example);
        System.out.println(count);
    }

    /**
     * 根据条件进行更新
     */
    @Test
    public void testUpdateByExample() {
        fail("Not yet implemented");
    }

}
