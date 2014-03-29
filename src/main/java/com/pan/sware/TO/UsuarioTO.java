package com.pan.sware.TO;

import com.pan.sware.db.DBConnectionManager;
import java.io.Serializable;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author estebanfcv
 */
public class UsuarioTO implements Serializable,Cloneable {
    
    private Connection conexion;
    
    private int id;
    private String username;
    private String password;
    
     private Date fechaIngreso;
    private Date fechaFinSesion;
    private String idSesion;
    private String ubicacion;
    
     @Override
     public UsuarioTO clone() {
        UsuarioTO clon = null;
        try {
            clon = (UsuarioTO) super.clone();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se puede duplicar");
        }
        return clon;
    }
     
       public void crearConexion() {
        conexion=null;
        conexion = DBConnectionManager.getInstance().getConnection(DBConnectionManager.BD);
    }
      
      public void asignarfechasSesion() {
        Calendar cal = Calendar.getInstance();
        this.fechaIngreso = cal.getTime();
//        cal.add(Calendar.MILLISECOND, (int) (this.perfil.getTiempoSesion()));
        cal.add(Calendar.MILLISECOND, (int) (6000000));
        this.fechaFinSesion = cal.getTime();
        System.out.println("FECHA INICIO SESION::::::" + fechaIngreso);
        System.out.println("FECHA FIN SESION:::::::::" + fechaFinSesion);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaFinSesion() {
        return fechaFinSesion;
    }

    public void setFechaFinSesion(Date fechaFinSesion) {
        this.fechaFinSesion = fechaFinSesion;
    }

    public String getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(String idSesion) {
        this.idSesion = idSesion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
}
