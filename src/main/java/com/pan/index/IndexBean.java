package com.pan.index;

import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.Util.ParametroCache;
import com.pan.sware.Util.Util;
import com.pan.sware.sesiones.ManejadorSesiones;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author estebanfcv
 */
@ManagedBean(name = "index")
@ViewScoped
public class IndexBean implements Serializable {

    private UsuarioTO usuario;
    private String mensajeError;
    private String color;
    private RecuperarPassword recuperarPassword;

    public IndexBean() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    private void inicializar() {
        recuperarPassword = new RecuperarPassword();
        usuario = new UsuarioTO();
    }

    public String consultarUsuario() {
        try {
            if (validarCamposVacios()) {
                usuario = ParametroCache.obtenerUsuarioTOPorUserPass(usuario.getUsername(), Util.encryptMD5(usuario.getPassword()));
                if (usuario != null) {
                    if (usuario.getId() == 1) {
                        usuario = new PermisosUsuario().establecerPermisosUsuarioMaestro(usuario);
                    }
                    ManejadorSesiones.agregarSesion(usuario);
                    return "cuenta";
                } else {
                    if (ParametroCache.isUsuarioCoordinacion(usuario.getUsername(), Util.encryptMD5(usuario.getPassword())) != null) {
                        usuario = new PermisosUsuario().establecerPermisosCoordinacion(usuario);
                        ManejadorSesiones.agregarSesion(usuario);
                    } else {
                        mensajeError = "El username y/o el password son incorrectos.";
                        color = "color: red";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    public boolean validarCamposVacios() {
        if (usuario.getUsername().isEmpty()) {
            mensajeError = "Favor de escribir el username.";
            color = "color: red";
            return false;
        }
        if (usuario.getPassword().isEmpty()) {
            mensajeError = "Favor de escribir el password.";
            color = "color: red";
            return false;
        }
        return true;
    }

    public void limpiar() {
        mensajeError = "";
        color = "color: green";
        inicializar();

    }

    public void abrirPopUpRecuperarPassword() {

        System.out.println("Recuperar password");
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

    public RecuperarPassword getRecuperarPassword() {
        return recuperarPassword;
    }
}
