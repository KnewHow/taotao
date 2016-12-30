package com.taotao.manage.datasource;

/**
 * 使用ThreadLocal来记录当前线程中数据源的key
 * 
 * @author Administrator
 *
 */
public class DynamicDataSourceHolder {
    // 写库对应的数据源的key
    private static final String MASTER = "master";

    // 读库对应的数据源的key
    private static final String SLAVE = "slave";

    //使用ThreadLocal记录当前线程中的数据源
    private static final ThreadLocal<String> HOLDER = new ThreadLocal<String>();
    
    /**
     * 设置数据源key
     * @param key
     */
    public static void putDataSourceKey(String key){
        HOLDER.set(key);
    }
    
    /**
     * 获取数据源的key
     * @return
     */
    public static String getDataSourceKey(){
        return HOLDER.get();
    }
    
    
    /**
     * 标记写库
     */
    public static void markMaster(){
        putDataSourceKey(MASTER);
    }
    
    /**
     * 标记写库
     */
    public static void markSlave(){
        putDataSourceKey(SLAVE);
    }
}
