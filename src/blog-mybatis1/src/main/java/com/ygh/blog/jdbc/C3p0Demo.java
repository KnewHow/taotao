package com.ygh.blog.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class C3p0Demo {

    @Test
    public void test() {
        for (int i = 0; i <= 20; i++) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = C3p0Utils.getConnection();
                String sql = "select *from tb_content";
                preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                int sum = 0;
                while (resultSet.next()) {
                    sum++;
                    System.out.println(resultSet.getString("title"));
                }
                System.out.println(sum);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }

    }


}
