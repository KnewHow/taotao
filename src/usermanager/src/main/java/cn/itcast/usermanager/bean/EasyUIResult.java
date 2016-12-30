package cn.itcast.usermanager.bean;

import java.util.List;

public class EasyUIResult {

    
    
    public EasyUIResult(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }
    
    public EasyUIResult() {
        
    }

    private Long total;//总记录数
    
    private List<?> rows;//使用list来封装记录数

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
    
    
}
