package com.pan.sware.TO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author estebanfcv
 */
public class CoordinacionTO {
    
    private List<CoordinacionMunicipioTO> listaMunicipios = new ArrayList<CoordinacionMunicipioTO>();
    private int id;
    private String nombre="";
    private String nombreResponsable="";
    private String apellidoPaterno="";
    private String apellidoMaterno="";
    private String calle="";
    private String numeroExterior="";
    private String numeroInterior="";
    private String colonia="";
    private int codigoPostal;
    private String telefono="";
    private String email="";
    private Date fechaAlta;
    private byte[] avatar;

    public List<CoordinacionMunicipioTO> getListaMunicipios() {
        return listaMunicipios;
    }

    public void setListaMunicipios(List<CoordinacionMunicipioTO> listaMunicipios) {
        this.listaMunicipios = listaMunicipios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public int getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(int codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
