package com.pan.sware.directorio;

import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.Util.ParametroCache;
import com.pan.sware.Util.Util;
import com.pan.sware.sesiones.ManejadorSesiones;
import java.util.List;
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
    private boolean tablaVisible;
    private byte filas;
    private boolean todos;

    public DirUsuarios() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    private void inicializar() {

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
            System.out.println("entre");
            mensajeError = "";
            todos = (boolean) event.getNewValue();
            System.out.println("todos vale::: " + todos);
            for (UsuarioTO u : listaUsuarios) {
                u.setMandarEmail(todos);
            }
        }
    }

    public void abrirPopUp() {
        popUpEmail = new PopUpEmail();
        String lista = "";
        for (UsuarioTO u : listaUsuarios) {
            if (u.isMandarEmail()) {
                lista += u.getEmail() + ",";
            }
        }
        popUpEmail.setListaCorreos(lista);
        popUpEmail.abrirPopUp();
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

}
