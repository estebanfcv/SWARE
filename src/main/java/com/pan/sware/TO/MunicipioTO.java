package com.pan.sware.TO;

/**
 *
 * @author estebanfcv
 */
public class MunicipioTO {
    
    private short id;
    private String nombre="";
    private byte idEstado;

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre.trim();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(byte idEstado) {
        this.idEstado = idEstado;
    }
}
