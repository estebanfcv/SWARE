package com.pan.sware.TO;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author estebanfcv
 */
public class AgendaTO implements Cloneable, Serializable {

    private int id;
    private String titulo = "";
    private String mensaje = "";
    private Date fecha = new Date();
    private String horas = "";
    private String minutos = "";
    private Date fechaAlta;
    private int idUsuario;
    private int idCoordinacion;

    @Override
    public AgendaTO clone() {
        AgendaTO clon = null;
        try {
            clon = (AgendaTO) super.clone();
        } catch (Exception e) {
            System.out.println("No se puede duplicar");
        }
        return clon;
    }

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
