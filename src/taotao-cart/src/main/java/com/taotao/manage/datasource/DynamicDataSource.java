package com.taotao.manage.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource{

    @Override
    protected Object determineCurrentLookupKey() {
        /*
         * 使用DynamicDataSourceHolder保证线程安全，并且获取当前数据源的key
         */
        return DynamicDataSourceHolder.getDataSourceKey();
    }

}
