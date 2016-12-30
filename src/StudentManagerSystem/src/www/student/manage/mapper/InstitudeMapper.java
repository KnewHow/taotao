package www.student.manage.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import www.student.manage.pojo.Institute;
import cn.itcast.jdbc.TxQueryRunner;

public class InstitudeMapper {
    
    private QueryRunner qr = new TxQueryRunner();
    
    public List<Institute> getInstitudes() throws SQLException{
        String sql = "select *from institute";
        List<Institute> institutes = qr.query(sql, new BeanListHandler<Institute>(Institute.class));
        return institutes;
    }
}
