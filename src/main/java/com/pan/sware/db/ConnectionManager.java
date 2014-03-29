package com.pan.sware.db;


import java.sql.*;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author estebanfcv
 */
public  class  ConnectionManager {
    
    public boolean debug = false;
    public static int conexionesTotalesAbiertas = 0;
    private String jndiUrl;
    
    public void setJndiVariables(String jndiUrl) {
        this.jndiUrl = jndiUrl;
    }
    
//    public static Connection getConnection(){
//        Connection con =null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//             con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SWARE", "root", "swareDB");
//             
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
//          e.printStackTrace();
//            
//        }
//        return con;
//    }
    
     public Connection getConexion() {
        try {
            Context ctxContexto = new InitialContext();
            System.out.println("El contexto es:::::"+ctxContexto.lookup(jndiUrl));
            DataSource dsOrigenDatos = (DataSource) ctxContexto.lookup(jndiUrl);
            ctxContexto.close();
            return dsOrigenDatos.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public static int getConexionesTotalesAbiertas() {
        return conexionesTotalesAbiertas;
    }

    public static void setConexionesTotalesAbiertas(int conexionesTotalesAbiertas) {
        ConnectionManager.conexionesTotalesAbiertas = conexionesTotalesAbiertas;
    }
    
}
