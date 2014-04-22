package com.pan.sware.TO;

import java.util.Date;

/**
 *
 * @author estebanfcv
 */
public class AgendaTO {
    
    private int id;
    private String titulo="";
    private String mensaje="";
    private Date fecha;
    private String hora="";
    private String horas="";
    private String minutos="";
    private Date fechaAlta;
    private int idUsuario;
    private int idCoordinacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getMinutos() {
        return minutos;
    }

    public void setMinutos(String minutos) {
        this.minutos = minutos;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCoordinacion() {
        return idCoordinacion;
    }

    public void setIdCoordinacion(int idCoordinacion) {
        this.idCoordinacion = idCoordinacion;
    }
    
    
    
}
