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
public class UsuarioTO implements Serializable, Cloneable {

    private Connection conexion;

    // TO
    PerfilTO perfil;

    private int id;
    private String username="";
    private String password="";
    private String email="";
    private byte[] avatar = new byte[0];
    private String nombre="";
    private String apellidoPaterno="";
    private String apellidoMaterno="";
    private int idCoordinacion;
    private int idPerfil;
    private Date fechaAlta;
    private String telefonoCasa="";
    private String telefonoOficina="";
    private String telefonoCelular="";

    private Date fechaIngreso;
    private Date fechaFinSesion;
    private String idSesion="";
    private String ubicacion="";

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
        conexion = null;
        conexion = DBConnectionManager.getInstance().getConnection(DBConnectionManager.BD);
        try {
            conexion.setAutoCommit(false);
            System.out.println("la conexion es::::::::: " + conexion.getAutoCommit());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return username.trim();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password.trim();
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
        return idSesion.trim();
    }

    public void setIdSesion(String idSesion) {
        this.idSesion = idSesion;
    }

    public String getUbicacion() {
        return ubicacion.trim();
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Connection getConexion() {
        try {
            conexion.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public String getEmail() {
        return email.trim();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getNombre() {
        return nombre.trim();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno.trim();
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno.trim();
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public int getIdCoordinacion() {
        return idCoordinacion;
    }

    public void setIdCoordinacion(int idCoordinacion) {
        this.idCoordinacion = idCoordinacion;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getTelefonoCasa() {
        return telefonoCasa.trim();
    }

    public void setTelefonoCasa(String telefonoCasa) {
        this.telefonoCasa = telefonoCasa;
    }

    public String getTelefonoOficina() {
        return telefonoOficina.trim();
    }

    public void setTelefonoOficina(String telefonoOficina) {
        this.telefonoOficina = telefonoOficina;
    }

    public String getTelefonoCelular() {
        return telefonoCelular.trim();
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public PerfilTO getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilTO perfil) {
        this.perfil = perfil;
    }
}
