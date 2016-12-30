package com.taotao.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;
/**
 * 
 * @author Administrator
 *方法：
    1、queryById
    2、queryAll
    3、queryOne
    4、queryListByWhere
    5、queryPageListByWhere
    6、save
    7、update
    8、deleteById
    9、deleteByIds
    10、deleteByWhere
 * @param <T>
 */
public abstract class NewBaseService<T extends BasePojo> {

    /**
     * 在spring4.0中增添了泛型注入这个新特性，父类去子类中查找mapper，从而进行自动注入
     */
    @Autowired
    private Mapper<T> mapper;
    
    /**
     * 根据id查询查询数据
     * @param id
     * @return
     */
    public T queryById(Integer id){
        return this.mapper.selectByPrimaryKey(id);
    }
    
    /**
     * 查询所以的记录数
     * @return
     */
    public List<T> queryAll(){
        return this.mapper.select(null);
    }
    
    /**
     * 根据条件查询一条数据，如果有多条数据就会抛出异常
     * @param record
     * @return
     */
    public T queryOne(T record){
        return this.mapper.selectOne(record);
    }
    
    /**
     * 根据条件来查询数据
     * @param record
     * @return
     */
    public List<T> queryListByWhere(T record){
        return this.mapper.select(record);
    }
    
    /**
     * 按条件进行分页查询数据
     * @param page
     * @param rows
     * @param record
     * @return
     */
    public PageInfo<T> queryPageListByWhere(Integer page,Integer rows,T record){
        PageHelper.startPage(page, rows);
        List<T> list = this.mapper.select(record);
        return new PageInfo<T>(list);
    }
    
    /**
     * 保存数据
     * @param record
     * @return
     */
    public Integer save(T record){
        record.setCreated(new Date());
        record.setUpdated(record.getCreated());
        return this.mapper.insert(record);
    }
    
    
    /**
     * 有选择的保存数据
     * @param record
     * @return
     */
    public Integer savaSelective(T record){
        record.setCreated(new Date());
        record.setUpdated(record.getCreated());
        return this.mapper.insertSelective(record);
    }
    
    /**
     * 保存数据，必须传入主键，不然会报错
     * @param record
     * @return
     */
    public Integer update(T record){
        record.setUpdated(new Date());
        return this.mapper.updateByPrimaryKey(record);
    }
    
    /**
     * 有选择性的保存数据，只对非空数据进行保存
     * @param record
     * @return
     */
    public Integer updateSelective(T record){
        record.setUpdated(new Date());
        return this.mapper.updateByPrimaryKeySelective(record);
    }
    
    /**
     * 根据主键删除id
     * @param id
     * @return
     */
    public Integer deleteById(Long id){
       return this.mapper.deleteByPrimaryKey(id);
    }
    
    /**
     * 批量删除数据
     * @param clazz
     * @param property
     * @param values
     * @return
     */
    public Integer deleteByIds(Class<T> clazz,String property,List<Object> values){
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, values);
        return this.mapper.deleteByExample(example);
    }
    
    /**
     * 根据条件删除数据
     * @param record
     * @return
     */
    public Integer deleteByWhere(T record){
       return this.mapper.delete(record);
    }
    
}
