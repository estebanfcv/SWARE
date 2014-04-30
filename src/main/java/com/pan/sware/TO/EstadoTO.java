package com.pan.sware.TO;

/**
 *
 * @author estebanfcv
 */
public class EstadoTO {
    
    private byte id;
    private String nombre="";

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre.trim();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
