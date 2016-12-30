package com.ygh.blog.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0Utils {

    
    public static Connection getConnection() throws SQLException{
        return getDdataSource().getConnection();
    }
    
    public static ComboPooledDataSource getDdataSource(){
        return new ComboPooledDataSource();
    }
}
