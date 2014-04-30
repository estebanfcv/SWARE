package com.pan.sware.catalogos.campania;

import com.pan.sware.TO.CampaniaTO;
import com.pan.sware.Util.Constantes;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author estebanfcv
 */
@ManagedBean(name = "catCamp")
@ViewScoped
public class CampaniaBean implements Serializable {

    private CampaniaTO campania;
    private List<CampaniaTO> listaCampanias;
    private PopUpMunicipios popUpMunicipios;
    private PopUpAvatar popUpAvatar;
    private String mensajeError;
    private String mensajeBoton;
    private String color;
    private boolean tablaVisible;
    private CampaniaDAO daoCampania;
    private int filas;
    private boolean popUpEliminar;
  

    public CampaniaBean() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    private void inicializar() {
        daoCampania = new CampaniaDAO();
        campania = new CampaniaTO();
        popUpAvatar = new PopUpAvatar(campania);
        popUpMunicipios = new PopUpMunicipios(campania);
        mensajeBoton = Constantes.BOTON_AGREGAR;
        filas = 10;
        popUpEliminar = false;
        consultar();

    }

    private void consultar() {
        listaCampanias = daoCampania.obtenerListaCampanias();
        if (!listaCampanias.isEmpty()) {
            tablaVisible = true;
        } else {
            tablaVisible = false;
            mensajeError = "No hay campañas.";
            color = "color: red";
        }
    }

    public void actionAgregarModificar() {
        if (validarCamposObligatorios()) {
            if (mensajeBoton.equals(Constantes.BOTON_AGREGAR)) {
                if (validarDatos()) {
                    if (daoCampania.insertarCampania(campania)) {
                        mensajeError = "Inserción Exitosa";
                        color = "color: green";
                        // parametro cache inicializar campanias 
                        inicializar();
                    } else {
                        mensajeError = "La inserción no se pudo realizar";
                        color = "color: red";
                    }
                }
            } else {
                if (validarModificaciones()) {
                    if (daoCampania.modificarCampania(campania)) {
                        mensajeError = "La campaña se modificó exitosamente";
                        color = "color: green";
                        // parametro cache inicializar campanias 
                        inicializar();
                    } else {
                        mensajeError = "La campaña no se pudo modificar";
                        color = "color: red";
                    }
                }
            }
        }

    }

    private boolean validarCamposObligatorios() {
        if (campania.getAvatar().length == 0) {
            mensajeError = "Favor de elegir su imagen";
            color = "color: red";
            return false;
        }
        if (campania.getNombre().isEmpty()) {
            mensajeError = "Favor de escribir el nombre.";
            color = "color: red";
            return false;
        }
        if (campania.getFecha() == null) {
            mensajeError = "Favor de seleccionar la fecha.";
            color = "color: red";
            return false;
        }
        if (campania.getListaMunicipios().isEmpty()) {
            mensajeError = "Favor de asignar al menos un municipio";
            color = "color: red";
            return false;
        }
        return true;
    }

    private boolean validarDatos() {
        for (CampaniaTO c : listaCampanias) {
            if (c.getNombre().equals(campania.getNombre())) {
                mensajeError = "El nombre ya existe.";
                color = "color: red";
                return false;
            }
            if (c.getFecha().compareTo(campania.getFecha()) == 0) {
                mensajeError = "La fecha ya existe.";
                color = "color: red";
                return false;
            }
        }
        return true;
    }

    private boolean validarModificaciones() {
        for (CampaniaTO c : listaCampanias) {
            if (c.getId() == campania.getId()) {
                if (campania.getListaMunicipios().isEmpty()) {
                    mensajeError = "Favor de asignar al menos un municipio";
                    color = "color: red";
                    return false;
                }
                continue;
            }
            if (c.getNombre().equals(campania.getNombre())) {
                mensajeError = "El nombre ya existe.";
                color = "color: red";
                return false;
            }
            if (c.getFecha().compareTo(campania.getFecha()) == 0) {
                mensajeError = "La fecha ya existe.";
                color = "color: red";
                return false;
            }
        }
        return true;
    }

    public void actionListenerModificar(ActionEvent event) {
        campania = ((CampaniaTO) event.getComponent().getAttributes().get("campania")).clone();
        popUpMunicipios = new PopUpMunicipios(campania);
        popUpAvatar = new PopUpAvatar(campania);
        mensajeError = "";
        mensajeBoton = Constantes.BOTON_MODIFICAR;
    }

    public void limpiar() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    public void abrirPopUpEliminar(ActionEvent event) {
        campania = ((CampaniaTO) event.getComponent().getAttributes().get("campania"));
        popUpEliminar = true;
    }

    public void cerrarPopUpEliminar() {
        popUpEliminar = false;
    }

    public void confirmarEliminarCampania() {
        if (daoCampania.eliminarCampania(campania)) {
            mensajeError = "La campaña se eliminó con éxito";
            color = "color: green";
            // parametro cache inicializar campanias
            inicializar();
        } else {
            mensajeError = "La campaña no se pudo eliminar";
            color = "color: red";
        }
        popUpEliminar = false;
    }

    public CampaniaTO getCampania() {
        return campania;
    }

    public List<CampaniaTO> getListaCampanias() {
        return listaCampanias;
    }

    public PopUpMunicipios getPopUpMunicipios() {
        return popUpMunicipios;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public String getMensajeBoton() {
        return mensajeBoton;
    }

    public String getColor() {
        return color;
    }

    public boolean isTablaVisible() {
        return tablaVisible;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public boolean isPopUpEliminar() {
        return popUpEliminar;
    }

    public PopUpAvatar getPopUpAvatar() {
        return popUpAvatar;
    }

    public String getImagenDefault() {
        return Constantes.IMAGEN_DEFAULT;
    }

}
