/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pan.sware.db;


import java.sql.*;
import java.util.logging.Logger;

/**
 *
 * @author estebanfcv
 */
public  class  ConnectionManager {
    
    public static Connection getConnection(){
        Connection con =null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
             con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SWARE", "root", "swareDB");
             
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
          e.printStackTrace();
            
        }
        return con;
    }
    
}
