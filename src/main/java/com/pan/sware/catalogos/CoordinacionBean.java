package com.pan.sware.catalogos;

import com.pan.sware.TO.CoordinacionTO;
import com.pan.sware.Util.Constantes;
import com.pan.sware.Util.Util;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author estebanfcv
 */
@ManagedBean(name = "catCoor")
@ViewScoped
public class CoordinacionBean implements Serializable {

    private CoordinacionTO coordinacion;
    private CoordinacionDAO DaoCoor;
    private String mensajeError;
    private String color;
    private List<CoordinacionTO> listaCoordinaciones;
    private boolean tablaVisible;
    private String mensajeBoton;
    private CoordinacionPopUpMunicipios popUpMunicipio;
    private byte filas;
    private boolean popUpEliminar;

    public CoordinacionBean() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    private void inicializar() {
        coordinacion = new CoordinacionTO();
        popUpMunicipio = new CoordinacionPopUpMunicipios(coordinacion);
        DaoCoor = new CoordinacionDAO();
        tablaVisible = false;
        mensajeBoton = Constantes.BOTON_AGREGAR;
        filas = 20;
        popUpEliminar = false;
        consultar();

    }

    private void consultar() {
        listaCoordinaciones = DaoCoor.obtenerListaCoordinaciones();
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
            if (mensajeBoton.equals(Constantes.BOTON_AGREGAR)) {
                if (validarDatos()) {
                    if (DaoCoor.insertarCoordinacion(coordinacion)) {
                        mensajeError = "Inserción Exitosa";
                        color = "color: green";
                        inicializar();
                        // parametro cache inicializar coordinaciones 
                    } else {
                        mensajeError = "La inserción no se pudo realizar";
                        color = "color: red";
                    }
                }
            } else {
                if (validarModificaciones()) {
                    if (DaoCoor.modificarCoordinacion(coordinacion)) {
                        mensajeError = "La coordinacion se modificó exitosamente";
                        color = "color: green";
                        // parametro cache inicializar coordinaciones 
                        inicializar();
                    } else {
                        mensajeError = "La coordinacion no se pudo modificar";
                        color = "color: red";
                    }
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
        if (coordinacion.getUsername().isEmpty()) {
            mensajeError = "Favor de escribir su username";
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

        if (coordinacion.getListaMunicipios().isEmpty()) {
            mensajeError = "Favor de asignar al menos un municipio";
            color = "color: red";
            return false;
        }
        return true;
    }

    private boolean validarDatos() {
        for (CoordinacionTO c : listaCoordinaciones) {
            if (c.getUsername().equals(coordinacion.getUsername())) {
                mensajeError = "El username ya existe";
                color = "color: red";
                return false;
            }
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

    private boolean validarModificaciones() {
        for (CoordinacionTO c : listaCoordinaciones) {
            if (c.getId() == coordinacion.getId()) {
                continue;
            }
            if (c.getUsername().equals(coordinacion.getUsername())) {
                mensajeError = "El username ya existe";
                color = "color: red";
                return false;
            }
            if (c.getNombre().equals(coordinacion.getNombre())) {
                mensajeError = "El nombre de la coordinacion ya existe";
                color = "color: red";
                return false;
            }
            if (c.getEmail().equals(coordinacion.getEmail())) {
                mensajeError = "El email ya existe";
                color = "color: red";
                return false;
            }
        }
        return true;
    }

    public void actionListenerModificar(ActionEvent event) {
        coordinacion = ((CoordinacionTO) event.getComponent().getAttributes().get("coordinacion")).clone();
        popUpMunicipio = new CoordinacionPopUpMunicipios(coordinacion);
        mensajeError = "";
        mensajeBoton = Constantes.BOTON_MODIFICAR;
    }

    public void limpiar() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    public void abrirPopUpEliminar(ActionEvent event) {
        System.out.println("1");
        coordinacion = (CoordinacionTO) event.getComponent().getAttributes().get("coordinacion");
        System.out.println("2");
        popUpEliminar = true;
    }

    public void confirmarEliminarCoordinacion() {
        System.out.println("3");
        if (DaoCoor.eliminarCoordinacion(coordinacion)) {
            System.out.println("4");
            mensajeError = "La coordinación se eliminó con éxito";
            color = "color: green";
            // parametro cache inicializar coordinaciones
            inicializar();
        } else {
            System.out.println("5");
            mensajeError = "La coordinación no se pudo eliminar";
            color = "color: red";
        }
        popUpEliminar = false;
    }

    public void cerrarPopUpEliminar() {
        popUpEliminar = false;
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

    public boolean isTablaVisible() {
        return tablaVisible;
    }

    public byte getFilas() {
        return filas;
    }

    public void setFilas(byte filas) {
        this.filas = filas;
    }

    public List<CoordinacionTO> getListaCoordinaciones() {
        return listaCoordinaciones;
    }

    public boolean isPopUpEliminar() {
        return popUpEliminar;
    }
}
