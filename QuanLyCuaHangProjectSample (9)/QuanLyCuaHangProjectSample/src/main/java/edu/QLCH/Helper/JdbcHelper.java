/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.QLCH.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class JdbcHelper {
    public static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver"; 
    public static String dburl="jdbc:sqlserver://localhost:1433;databaseName=project2";
    public static String username="sa";
    public static String password="123";
    
    //nạp driver
    static{
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    /** 
     * Xây dưng PreparedStatement
     * @param sql là câu lệnh SQL có thể chứa tham số
     * @return PreparedStatement tao duoc
     * @throws java.sql.SQLException lỗi sai cú pháp
     */
    public static PreparedStatement getStmt(String sql, Object...args) throws SQLException{
        Connection connection = DriverManager.getConnection(dburl, username, password);
        PreparedStatement pstmt = null;
        if(sql.trim().startsWith("{")){
            pstmt = connection.prepareCall(sql);
        }else{
            pstmt = connection.prepareStatement(sql);
        }for(int i=0;i<args.length;i++){
            pstmt.setObject(i + 1, args[i]);
        }
        return pstmt;
    }
    public static void update(String sql, Object...args){
        try {
            PreparedStatement stmt = JdbcHelper.getStmt(sql, args);
            try {
                stmt.executeUpdate();
            } finally{
                stmt.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static ResultSet query(String sql, Object...args){ //SelectAll
        try {
            PreparedStatement stmt = JdbcHelper.getStmt(sql, args);
            return stmt.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    public static Object value(String sql, Object...args){ // selectById
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            if(rs.next()){
                return rs.getObject(0);
            }
            rs.getStatement().getConnection().close();
            return null;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
