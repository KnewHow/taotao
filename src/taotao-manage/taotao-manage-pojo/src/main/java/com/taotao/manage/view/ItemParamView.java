package com.taotao.manage.view;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.taotao.manage.pojo.BasePojo;


@Table(name = "tb_item_param")
public class ItemParamView extends BasePojo{

    private String itemCatName;//商品的名称
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//规格参数的ID

    private Long itemCatId;//商品类型的Id

    private String paramData;//规格参数的信息

    

    public String getItemCatName() {
        return itemCatName;
    }



    public void setItemCatName(String itemCatName) {
        this.itemCatName = itemCatName;
    }



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public Long getItemCatId() {
        return itemCatId;
    }



    public void setItemCatId(Long itemCatId) {
        this.itemCatId = itemCatId;
    }



    public String getParamData() {
        return paramData;
    }



    public void setParamData(String paramData) {
        this.paramData = paramData;
    }
    
    
    
    
    
    
    
}
