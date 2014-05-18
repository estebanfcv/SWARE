package com.pan.sware.TO;

import java.io.Serializable;

/**
 *
 * @author estebanfcv
 */
public class DirectorioTO implements Cloneable, Serializable {

    private int id;
    private String nombre = "";
    private String telefono1 = "";
    private String telefono2 = "";
    private String telefono3 = "";
    private String email = "";
    private String comentario = "";
    private int idCoordinacion;
    private boolean mandarEmail = false;

    @Override
    public DirectorioTO clone() {
        DirectorioTO clon = null;
        try {
            clon = (DirectorioTO) super.clone();
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

    public String getNombre() {
        return nombre.trim();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono1() {
        return telefono1.trim();
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2.trim();
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getTelefono3() {
        return telefono3.trim();
    }

    public void setTelefono3(String telefono3) {
        this.telefono3 = telefono3;
    }

    public String getEmail() {
        return email.trim();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComentario() {
        return comentario.trim();
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdCoordinacion() {
        return idCoordinacion;
    }

    public void setIdCoordinacion(int idCoordinacion) {
        this.idCoordinacion = idCoordinacion;
    }

    public boolean isMandarEmail() {
        return mandarEmail;
    }

    public void setMandarEmail(boolean mandarEmail) {
        this.mandarEmail = mandarEmail;
    }
}
