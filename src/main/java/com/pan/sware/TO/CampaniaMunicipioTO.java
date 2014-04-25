package com.pan.sware.TO;

import java.io.Serializable;

/**
 *
 * @author estebanfcv
 */
public class CampaniaMunicipioTO implements Cloneable, Serializable {
    
    private int idCoordinacion;
    private byte idEstado;
    private short idMunicipio;
    private int idCampania;
    
    @Override
    public CampaniaMunicipioTO clone() {
        CampaniaMunicipioTO clon = null;
        try {
            clon = (CampaniaMunicipioTO) super.clone();
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

    public int getIdCampania() {
        return idCampania;
    }

    public void setIdCampania(int idCampania) {
        this.idCampania = idCampania;
    }
    
    
}
