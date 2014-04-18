package com.pan.sware.TO;

import java.io.Serializable;

/**
 *
 * @author estebanfcv
 */
public class CoordinacionMunicipioTO implements Cloneable, Serializable {
    
    private int idCoordinacion;
    private byte idEstado;
    private short idMunicipio;
    
    @Override
    public CoordinacionMunicipioTO clone() {
        CoordinacionMunicipioTO clon = null;
        try {
            clon = (CoordinacionMunicipioTO) super.clone();
        } catch (Exception e) {
            System.out.println("No se puede duplicar");
        }
        return clon;
    }

    public int getIdCoordinacion() {
        return idCoordinacion;
    }

    public void setIdCoordinacion(int idCoordinacion) {
        this.idCoordinacion = idCoordinacion;
    }

    public byte getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(byte idEstado) {
        this.idEstado = idEstado;
    }

    public short getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(short idMunicipio) {
        this.idMunicipio = idMunicipio;
    }
}
