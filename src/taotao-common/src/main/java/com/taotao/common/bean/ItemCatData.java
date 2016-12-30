package com.taotao.common.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 序列化为json的前台的商品的分类的JavaBean，并使用了json注释
 * 在序列化成json的时候，可以进行重命名
 * @author Administrator
 *
 */
public class ItemCatData {

    @JsonProperty("u")//在序列化为json的时候，名称为u
    private String url;
    
    @JsonProperty("n")//在序列化为json的时候，名称为n
    private String name;
    
    @JsonProperty("i")//在序列化为json的时候，名称为i
    private List<?> items;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

    
}
