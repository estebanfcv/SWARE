package com.pan.index;

import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.sesiones.ManejadorSesiones;
import java.io.Serializable;
import java.util.Map;
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
    private Map<String, UsuarioTO> listaUsuarios;
    private IndexDAO ind;
    private String mensajeError;
    private String color;
    private byte[] bytes = new byte[0];
    private RecuperarPassword recuperarPassword;

    public IndexBean() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    private void inicializar() {
        recuperarPassword = new RecuperarPassword();
        usuario = new UsuarioTO();
        ind = new IndexDAO();
    }

    public String consultarUsuario() {
        if (validarCamposVacios()) {
            listaUsuarios = ind.obtenerListaUsuarios();
            if (listaUsuarios.get(usuario.getUsername() + "|" + usuario.getPassword()) != null) {
                usuario = listaUsuarios.get(usuario.getUsername() + "|" + usuario.getPassword());
                ManejadorSesiones.agregarSesion(usuario);
                System.out.println("PASASTE A LA SIGUIENTE PANTALLA :D");
                bytes = usuario.getAvatar();

                return "";
            } else {
                mensajeError = "El username y/o el password son incorrectos.";
                color = "color: red";
            }
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

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public RecuperarPassword getRecuperarPassword() {
        return recuperarPassword;
    }
}
