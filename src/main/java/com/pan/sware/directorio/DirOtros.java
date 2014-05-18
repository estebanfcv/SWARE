package com.pan.sware.directorio;

import com.pan.sware.TO.DirectorioTO;
import com.pan.sware.Util.Constantes;
import com.pan.sware.Util.Util;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author estebanfcv
 */
public class DirOtros {

    private List<DirectorioTO> listaDirectorio;
    private DirectorioDAO daoDir;
    private DirectorioTO directorio;
    private String mensajeError;
    private String color;
    private String mensajeBoton;
    private boolean tablaVisible;
    private int filas;
    private boolean todos;
    private PopUpEmail popUpEmail;
    private boolean popUpEliminar;

    public DirOtros() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    private void inicializar() {
        daoDir = new DirectorioDAO();
        directorio = new DirectorioTO();
        popUpEmail = new PopUpEmail();
        mensajeBoton = Constantes.BOTON_AGREGAR;
        filas = 10;
        todos = false;
        popUpEliminar = false;
        consultar();
    }

    private void consultar() {
        listaDirectorio = daoDir.obtenerDirectorio();
        if (!listaDirectorio.isEmpty()) {
            tablaVisible = true;
        } else {
            mensajeError = "No hay registros.";
            color = "color: red";
            tablaVisible = false;
        }
    }

    public void valueChangeCorreos(ValueChangeEvent event) {
        if (Util.isUpdatePhase(event)) {
            mensajeError = "";
            todos = (boolean) event.getNewValue();
            for (DirectorioTO d : listaDirectorio) {
                if (!d.getEmail().isEmpty()) {
                    d.setMandarEmail(todos);
                }
            }
        }
    }

    public void abrirPopUp() {
        popUpEmail = new PopUpEmail();
        String lista = "";
        for (DirectorioTO d : listaDirectorio) {
            if (d.isMandarEmail()) {
                lista += d.getEmail() + ", ";
            }
        }
        if (validarCamposObligatorios(lista)) {
            mensajeError = "";
            popUpEmail.setListaCorreos(lista);
            popUpEmail.abrirPopUp();
        }
    }

    private boolean validarCamposObligatorios(String lista) {
        if (lista.isEmpty()) {
            mensajeError = "Favor de seleccionar al menos un Email";
            color = "color: red";
            return false;
        }
        return true;
    }

    public void actionAgregarModificar() {
        if (validarCamposObligatorios()) {
            if (mensajeBoton.equals(Constantes.BOTON_AGREGAR)) {
                if (validarDatos()) {
                    if (daoDir.insertarRegistro(directorio)) {
                        mensajeError = "Inserción exitosa";
                        inicializar();
                    } else {
                        mensajeError = "La inserción no se pudo realizar";
                    }
                    color = mensajeError.contains("exitosa") ? "color: green" : "color: red";
                }
            } else {
                if (validarModificaciones()) {
                    if (daoDir.modificarRegistro(directorio)) {
                        mensajeError = "Modificación exitosa";
                        inicializar();
                    } else {
                        mensajeError = "La modificación no se pudo realizar";
                    }
                    color = mensajeError.contains("exitosa") ? "color: green" : "color: red";
                }
            }
        }
    }

    private boolean validarCamposObligatorios() {
        color = "color: red";
        if (directorio.getNombre().isEmpty()) {
            mensajeError = "Favor de escribir el nombre.";
            return false;
        }
        if (!directorio.getEmail().isEmpty()) {
            if (!Util.validarEmail(directorio.getEmail())) {
                mensajeError = "El Email no es válido.";
                return false;
            }
        }
        if (directorio.getTelefono1().isEmpty() && directorio.getTelefono2().isEmpty() && directorio.getTelefono3().isEmpty()) {
            mensajeError = "Favor de escribir al menos un teléfono.";
            return false;
        }
        return true;
    }

    private boolean validarDatos() {
        color = "color: red";
        for (DirectorioTO d : listaDirectorio) {
            if (d.getNombre().equals(directorio.getNombre())) {
                mensajeError = "El nombre ya existe.";
                return false;
            }
            if (!directorio.getEmail().isEmpty()) {
                if (d.getEmail().equals(directorio.getEmail())) {
                    mensajeError = "El Email ya existe.";
                }
            }
        }
        return true;
    }

    private boolean validarModificaciones() {
        color = "color: red";
        for (DirectorioTO d : listaDirectorio) {
            if (d.getId() == directorio.getId()) {
                continue;
            }
            if (d.getNombre().equals(directorio.getNombre())) {
                mensajeError = "El nombre ya existe.";
                return false;
            }
            if (!directorio.getEmail().isEmpty()) {
                if (d.getEmail().equals(directorio.getEmail())) {
                    mensajeError = "El Email ya existe.";
                    return false;
                }
            }
        }
        return true;
    }

    public void actionListenerModificar(ActionEvent event) {
        directorio = ((DirectorioTO) event.getComponent().getAttributes().get("directorio")).clone();
        mensajeError = "";
        mensajeBoton = Constantes.BOTON_MODIFICAR;
    }

    public void limpiar() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    public void abrirPopUpEliminar(ActionEvent event) {
        directorio = (DirectorioTO) event.getComponent().getAttributes().get("directorio");
        popUpEliminar = true;
    }

    public void confirmarEliminarRegistro() {
        if (daoDir.eliminarRegistro(directorio)) {
            mensajeError = "El registro se eliminó con éxito";
            inicializar();
        } else {
            mensajeError = "El registro no se pudo eliminar";
        }
        color = mensajeError.contains("éxito") ? "color: green" : "color: red";
        popUpEliminar = false;
    }

    public void cerrarPopUpEliminar() {
        popUpEliminar = false;
    }

    public List<DirectorioTO> getListaDirectorio() {
        return listaDirectorio;
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

    public boolean isTodos() {
        return todos;
    }

    public DirectorioTO getDirectorio() {
        return directorio;
    }

    public void setDirectorio(DirectorioTO directorio) {
        this.directorio = directorio;
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

    public void setTodos(boolean todos) {
        this.todos = todos;
    }

    public PopUpEmail getPopUpEmail() {
        return popUpEmail;
    }
}