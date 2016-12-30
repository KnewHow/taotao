package www.student.manage.test;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import www.student.manage.mapper.InstitudeMapper;
import www.student.manage.pojo.Institute;

public class TestDemo {

    @Test
    public void fun1() throws SQLException{
       List<Institute> institutes =  new InstitudeMapper().getInstitudes();
       System.out.println(institutes.toString());
    }
}
