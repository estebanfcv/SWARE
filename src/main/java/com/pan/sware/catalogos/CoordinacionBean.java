package com.pan.sware.catalogos;

import com.pan.sware.TO.CoordinacionTO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author estebanfcv
 */
@ManagedBean(name = "coordinacion")
@ViewScoped
public class CoordinacionBean implements Serializable {

    private CoordinacionTO coordinacion;

    public CoordinacionBean() {
        inicializar();
    }

    
    private void inicializar() {
        coordinacion = new CoordinacionTO();
    }
    
    public void insertar(){
        System.out.println("EL valor del nombre "+coordinacion.getNombre());
    }

    public CoordinacionTO getCoordinacion() {
        return coordinacion;
    }

    public void setCoordinacion(CoordinacionTO coordinacion) {
        this.coordinacion = coordinacion;
    }

    public void limpiar() {
    }
}
