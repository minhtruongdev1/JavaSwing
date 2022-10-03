/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.QLCH.Dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author TuanDuc
 */
public class getConnection {
    protected Connection con;
    
    public getConnection(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=project2";
            String s = System.getProperty("os.name");
            if(s.contains("Windows")){
                con = DriverManager.getConnection(url, "sa", "123");
            }else{
                con = DriverManager.getConnection(url, "sa", "123");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
