package com.pan.sware.catalogos.usuarios;

import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.Util.Constantes;
import com.pan.sware.Util.ParametroCache;
import com.pan.sware.Util.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author estebanfcv
 */
@ManagedBean(name = "catUser")
@ViewScoped
public class UsuariosBean implements Serializable {

    private UsuarioTO usuario;
    private UsuariosDAO daoUser;
    private String mensajeError;
    private String color;
    private String mensajeBoton;
    private boolean tablaVisible;
    private byte filas;
    private boolean popUpEliminar;
    private boolean popUpAvatar;
    private List<UsuarioTO> listaUsuarios;
    private List<SelectItem> comboPerfiles;

    public UsuariosBean() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    private void inicializar() {
        usuario = new UsuarioTO();
        daoUser = new UsuariosDAO();
        armarComboPerfiles();
        tablaVisible = false;
        mensajeBoton = Constantes.BOTON_AGREGAR;
        filas = 10;
        popUpEliminar = false;
        popUpAvatar=false;
        consultar();
    }

    private void armarComboPerfiles() {
        comboPerfiles = new ArrayList<>();
        comboPerfiles.add(new SelectItem(0, "Default"));
    }

    private void consultar() {
        listaUsuarios = daoUser.obtenerListaUsuarios();
        if (!listaUsuarios.isEmpty()) {
            tablaVisible = true;
        } else {
            tablaVisible = false;
            mensajeError = "No hay usuarios";
            color = "color: red";
        }
    }

    public void agregarModificar() {
        if (validarCamposObligatorios()) {
            if (mensajeBoton.equals(Constantes.BOTON_AGREGAR)) {
                if (validarDatos()) {
                    if (daoUser.insertarUsuario(usuario)) {
                        mensajeError = "El usuario se insertó con éxito";
                        ParametroCache.inicializarUsuarios();
                        inicializar();
                    } else {
                        mensajeError = "El usuario no se pudo insertar";
                    }
                    color = mensajeError.contains("éxito") ? "color: green" : "color: red";
                }
            } else {
                if (validarModificaciones()) {
                    if (daoUser.modificarUsuario(usuario)) {
                        mensajeError = "El usuario se modificó con éxito.";
                        ParametroCache.inicializarUsuarios();
                        inicializar();
                    } else {
                        mensajeError = "El usuario no se pudo modificar.";
                    }
                    color = mensajeError.contains("éxito") ? "color: green" : "color: red";
                }
            }
        }
    }

    private boolean validarCamposObligatorios() {
        color = "color: red";
        if (usuario.getUsername().isEmpty()) {
            mensajeError = "Favor de escribir el username";
            return false;
        }
        if (usuario.getEmail().isEmpty()) {
            mensajeError = "Favor de escribir el Email";
            return false;
        } else {
            if (!Util.validarEmail(usuario.getEmail())) {
                mensajeError = "El email no es válido";
                return false;
            }
        }
        if (usuario.getIdPerfil() == -1) {
            mensajeError = "Favor de seleccionar el perfil";
            return false;
        }
        if (usuario.getNombre().isEmpty()) {
            mensajeError = "Favor de escribir el nombre";
            return false;
        }
        if (usuario.getApellidoPaterno().isEmpty()) {
            mensajeError = "Favor de escribir el apellido paterno";
            return false;
        }
        if (usuario.getTelefonoCasa().isEmpty() && usuario.getTelefonoCelular().isEmpty()
                && usuario.getTelefonoTrabajo().isEmpty()) {
            mensajeError = "Favor de escribir al menos un teléfono";
            return false;
        }
        return true;
    }

    private boolean validarDatos() {
        color = "color: red";
        for (UsuarioTO u : listaUsuarios) {
            if (u.getUsername().equals(usuario.getUsername())) {
                mensajeError = "El username ya existe";
                return false;
            }
            if (u.getEmail().equals(usuario.getEmail())) {
                mensajeError = "El email ya existe.";
                return false;
            }
        }
        return true;
    }

    private boolean validarModificaciones() {
        color = "color: red";
        for (UsuarioTO u : listaUsuarios) {
            if (u.getId() == usuario.getId()) {
                continue;
            }
            if (u.getUsername().equals(usuario.getUsername())) {
                mensajeError = "El username ya existe";
                return false;
            }
            if (u.getEmail().equals(usuario.getEmail())) {
                mensajeError = "El email ya existe.";
                return false;
            }
        }
        return true;
    }

    public void actionListenerModificar(ActionEvent event) {
        usuario = ((UsuarioTO) event.getComponent().getAttributes().get("usuario")).clone();
        mensajeError = "";
        mensajeBoton = Constantes.BOTON_MODIFICAR;
    }

    public void limpiar() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    public void abrirPopUpEliminar(ActionEvent event) {
        usuario = (UsuarioTO) event.getComponent().getAttributes().get("usuario");
        popUpEliminar = true;
    }

    public void confirmarEliminarUsuario() {
        if (daoUser.eliminarUsuario(usuario)) {
            mensajeError = "El usuario se eliminó con éxito";
            ParametroCache.inicializarUsuarios();
            inicializar();
        } else {
            mensajeError = "El usuario no se pudo eliminar";
        }
        color = mensajeError.contains("éxito") ? "color: green" : "color: red";
    }
    
    public void abrirPopUpAvatar(){
        popUpAvatar=true;
    }
    
    public void cerrarPopUpAvatar(){
        popUpAvatar=false;
    }
    
    public void cerrarPopUp(){
        popUpEliminar=false;
    }

    public byte getFilas() {
        return filas;
    }

    public void setFilas(byte filas) {
        this.filas = filas;
    }

    public UsuarioTO getUsuario() {
        return usuario;
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

    public boolean isPopUpEliminar() {
        return popUpEliminar;
    }

    public List<UsuarioTO> getListaUsuarios() {
        return listaUsuarios;
    }

    public List<SelectItem> getComboPerfiles() {
        return comboPerfiles;
    }
    
    public String getImagenDefault(){
        return Constantes.IMAGEN_DEFAULT_USUARIO;
    }

    public boolean isPopUpAvatar() {
        return popUpAvatar;
    }
}