package com.pan.sware.directorio;

import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.Util.ParametroCache;
import com.pan.sware.Util.Util;
import com.pan.sware.sesiones.ManejadorSesiones;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author estebanfcv
 */
public class DirUsuarios {

    private PopUpEmail popUpEmail;
    private String mensajeError;
    private String color;
    private List<UsuarioTO> listaUsuarios;
    private UsuarioTO usuario;
    private boolean tablaVisible;
    private byte filas;
    private boolean todos;

    public DirUsuarios() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    private void inicializar() {
        usuario = new UsuarioTO();
        popUpEmail = new PopUpEmail();
        filas = 10;
        consultar();
    }

    private void consultar() {
        listaUsuarios = ParametroCache.obtenerUsuariosPorCoordinacion(ManejadorSesiones.getUsuario().getIdCoordinacion());
        if (!listaUsuarios.isEmpty()) {
            tablaVisible = true;
        } else {
            mensajeError = "No hay usuarios";
            color = "color: red";
            tablaVisible = false;
        }
    }

    public void valueChangeCorreos(ValueChangeEvent event) {
        if (Util.isUpdatePhase(event)) {
            mensajeError = "";
            todos = (boolean) event.getNewValue();
            for (UsuarioTO u : listaUsuarios) {
                u.setMandarEmail(todos);
            }
        }
    }
    
        public void actionListenerUsuario(ActionEvent event) {
        usuario = ((UsuarioTO) event.getComponent().getAttributes().get("usuario")).clone();
        mensajeError = "";
    }

    public void abrirPopUp() {
        popUpEmail = new PopUpEmail();
        String lista = "";
        for (UsuarioTO u : listaUsuarios) {
            if (u.isMandarEmail()) {
                lista += u.getEmail() + ", ";
            }
        }
        if (validarCamposObligatorios(lista)) {
            mensajeError="";
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

    public PopUpEmail getPopUpEmail() {
        return popUpEmail;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public String getColor() {
        return color;
    }

    public List<UsuarioTO> getListaUsuarios() {
        return listaUsuarios;
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

    public boolean isTodos() {
        return todos;
    }

    public void setTodos(boolean todos) {
        this.todos = todos;
    }

    public UsuarioTO getUsuario() {
        return usuario;
    }
}
