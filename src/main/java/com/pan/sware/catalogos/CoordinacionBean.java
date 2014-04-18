package com.pan.sware.catalogos;

import com.pan.sware.TO.CoordinacionTO;
import com.pan.sware.Util.Constantes;
import com.pan.sware.Util.Util;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author estebanfcv
 */
@ManagedBean(name = "catCoor")
@ViewScoped
public class CoordinacionBean implements Serializable {

    private CoordinacionTO coordinacion;
    private CoordinacionDAO coor;
    private String mensajeError;
    private String color;
    private List<CoordinacionTO> listaCoordinaciones;
    private boolean tablaVisible;
    private String mensajeBoton;
    private CoordinacionPopUpMunicipios popUpMunicipio;

    public CoordinacionBean() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    private void inicializar() {

        coordinacion = new CoordinacionTO();
        popUpMunicipio= new CoordinacionPopUpMunicipios(coordinacion);
        coor = new CoordinacionDAO();
        tablaVisible = false;
        mensajeBoton = Constantes.AGREGAR;
        consultar();

    }

    private void consultar() {
        listaCoordinaciones = coor.obtenerListaCoordinaciones();
        if (!listaCoordinaciones.isEmpty()) {
            tablaVisible = true;
        } else {
            tablaVisible = false;
            mensajeError = "No hay coordinaciones.";
            color = "color: red";
        }

    }

    public void actionAgregarModificar() {
        if (validarCamposObligatorios()) {
            if (mensajeBoton.equals(Constantes.AGREGAR)) {
                if (validarDatos()) {

                }

            }
        }
    }

    private boolean validarCamposObligatorios() {
        if (coordinacion.getNombre().isEmpty()) {
            mensajeError = "Favor de escribir el nombre de la coordinacion.";
            color = "color: red";
            return false;
        }
        if (coordinacion.getNombreResponsable().isEmpty()) {
            mensajeError = "Favor de escribir el nombre del responsable.";
            color = "color: red";
            return false;
        }
        if (coordinacion.getApellidoPaterno().isEmpty()) {
            mensajeError = "Favor de escribir el apellido paterno.";
            color = "color: red";
            return false;
        }
        if (coordinacion.getCalle().isEmpty()) {
            mensajeError = "Favor de escribir la calle.";
            color = "color: red";
            return false;
        }
        if (coordinacion.getColonia().isEmpty()) {
            mensajeError = "Favor de escribir la colonia.";
            color = "color: red";
            return false;
        }
        if (coordinacion.getNumeroExterior().isEmpty()) {
            mensajeError = "Favor de escribir el número exterior.";
            color = "color: red";
            return false;
        }
        if (coordinacion.getCodigoPostal() == 0) {
            mensajeError = "Favor de escribir el código postal.";
            color = "color: red";
            return false;
        }
        if (coordinacion.getEmail().isEmpty()) {
            mensajeError = "Favor de escribir el email.";
            color = "color: red";
            return false;
        } else {
            if (!Util.validarEmail(coordinacion.getEmail())) {
                mensajeError = "El email no es valido";
                color = "color: red";
                return false;
            }
        }
        if (coordinacion.getTelefono().isEmpty()) {
            mensajeError = "Favor de escribir el teléfono.";
            color = "color: red";
            return false;
        }
        
        if(coordinacion.getListaMunicipios().isEmpty()){
            mensajeError = "Favor de asignar al menos un municipio";
            color = "color: red";
            return false;
        }
        return true;
    }

    public boolean validarDatos() {
        for (CoordinacionTO c : listaCoordinaciones) {
            if (c.getNombre().equals(coordinacion.getNombre())) {
                mensajeError = "El nombre de la coordinacion ya existe";
                color = "color: red";
                return false;
            }
            if (c.getEmail().equals(coordinacion.getEmail())) {
                mensajeError = "El email ya existe.";
                color = "color: red";
                return false;
            }
        }
        return true;
    }

    public void limpiar() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    public CoordinacionTO getCoordinacion() {
        return coordinacion;
    }

    public void setCoordinacion(CoordinacionTO coordinacion) {
        this.coordinacion = coordinacion;
    }

    public CoordinacionPopUpMunicipios getPopUpMunicipio() {
        return popUpMunicipio;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public String getColor() {
        return color;
    }

    public String getMensajeBoton() {
        return mensajeBoton;
    }
    
    

}
