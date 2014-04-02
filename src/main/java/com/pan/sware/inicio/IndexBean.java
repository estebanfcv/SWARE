package com.pan.sware.inicio;

import com.pan.sware.DAO.LoginDAO;
import com.pan.sware.TO.UsuarioTO;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author estebanfcv
 */

public class IndexBean implements Serializable {

    private List<UsuarioTO> listaUsuario = new ArrayList<>();
    private String nombre;
    private String password;
    private String mensajeError;
    private String imagen;
    private boolean encontrado;

    private void inicializar() {
        mensajeError = "";
        password = "";
        nombre = "";
        imagen = "/imagenes/logo2.png";
        listaUsuario = new LoginDAO().consultarUsuarios();
        encontrado = false;
    }

    public IndexBean() {
        inicializar();
    }

    public void limpiar() {
        inicializar();
    }

    public String verificarUsuario() {
        if (nombre.isEmpty() || password.isEmpty()) {
            mensajeError = "El usuario y la contraseña son obligatorios.";
            return "";
        } else {
            for (UsuarioTO usuario : listaUsuario) {
                if (usuario.getUsername().equals(nombre) && usuario.getPassword().equals(password)) {
                    encontrado = true;
                    return "/principal.xhtml";
                }
            }
            if (!encontrado) {
                mensajeError = "El usuario y/o la contraseña son incorrectos.";
            }
            return "";
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public String getImagen() {
        return imagen;
    }
}
