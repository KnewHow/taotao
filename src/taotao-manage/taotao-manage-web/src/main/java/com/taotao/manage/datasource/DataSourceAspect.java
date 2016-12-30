package com.taotao.manage.datasource;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;

/**
 * 定义数据源的AOP切面，通过该service的方法名判断选择的写库还是读库
 * @author Administrator
 *
 */
public class DataSourceAspect {

    /**
     * 在进入service方法前执行
     * @param joinPoint
     */
    public void before(JoinPoint joinPoint){
        //获取到当前执行的方法名
        String methodName = joinPoint.getSignature().getName();
        if(isSalve(methodName)){
            //标记为读库
            DynamicDataSourceHolder.markSlave();
        }else{
            //标记为写库
            DynamicDataSourceHolder.markMaster();
        }
    }
    
    private Boolean isSalve(String methodName){
        return StringUtils.startsWithAny(methodName, "query","find","get","select");
    }
}
