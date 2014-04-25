package com.pan.sware.TO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author estebanfcv
 */
public class CampaniaTO implements Cloneable, Serializable {
    private List<CampaniaMunicipioTO> listaMunicipios = new ArrayList<>();
    private int id;
    private String nombre;
    private String Comentario;
    private Date fecha;
    private Date fechaAlta;
    private int idCoordinacion;
    private byte[] avatar = new byte[0];
    
     @Override
    public CampaniaTO clone() {
        CampaniaTO clon = null;
        try {
            clon = (CampaniaTO) super.clone();
        } catch (Exception e) {
            System.out.println("No se puede duplicar");
        }
        return clon;
    }

    public List<CampaniaMunicipioTO> getListaMunicipios() {
        return listaMunicipios;
    }

    public void setListaMunicipios(List<CampaniaMunicipioTO> listaMunicipios) {
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

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String Comentario) {
        this.Comentario = Comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public int getIdCoordinacion() {
        return idCoordinacion;
    }

    public void setIdCoordinacion(int idCoordinacion) {
        this.idCoordinacion = idCoordinacion;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
