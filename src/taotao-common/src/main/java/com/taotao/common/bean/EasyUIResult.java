package com.taotao.common.bean;

import java.io.IOException;
import java.util.List;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 返回的结果集类型
 * 
 * @author Administrator
 *
 */
public class EasyUIResult {

    private Integer total;// 查询返回的总行数

    private List<?> rows;// 查询返回的结果集

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public EasyUIResult() {

    }

    public EasyUIResult(Integer total, List<?> rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

    public EasyUIResult(Long total, List<?> rows) {
        super();
        this.total = total.intValue();
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    /**
     * 集合转换
     * 
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static EasyUIResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            //获取rows的数据
            JsonNode data = jsonNode.get("rows");
            List<?> list = null;
            if (data.isArray() && data.size() > 0) {
                //下面代码会报错，不知道为什么，求大神指点
                list = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionLikeType(List.class, clazz));
            }
            //生成对应的EasyUIResult对象
            return new EasyUIResult(jsonNode.get("total").intValue(), list);
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    
    

}
