package com.taotao.web.bean;

import org.apache.commons.lang3.StringUtils;

public class Item extends com.taotao.manage.pojo.Item {

    public String[] getImages(){
        /*
         * 使用java自带的split效率太低，因为java自带的split支持字符和正则表达式
         * 使用StringUtils的split的效率更高。而且不需要判断，在StringUtils.split
         * 内部自动进行判断，如果为null，直接返回null
         */
        return StringUtils.split(super.getImage(), ',');
    }
}
