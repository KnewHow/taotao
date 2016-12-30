package www.student.manage.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class JdbcTest {

    @Test
    public void queryInstitute(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = JdbcUtils.getConnection();
            String sql = "select *from institute";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getInt("i_id")+":"+resultSet.getString("i_name")+":"+resultSet.getString("i_manager"));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }finally{
            try {
                if(preparedStatement!=null){
                    preparedStatement.close();
                } 
                
                if(connection!=null){
                    connection.close();
                }
            }catch (SQLException e) {
                    e.printStackTrace();
            }
            
            
            
            
            
            
        }
        
    }
}
