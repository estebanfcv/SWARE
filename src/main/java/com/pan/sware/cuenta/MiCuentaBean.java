package com.pan.sware.cuenta;

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
@ManagedBean(name = "cuenta")
@ViewScoped
public class MiCuentaBean implements Serializable {

    private PopUpCambiarAvatar cambiarAvatar;
    private UsuarioTO usuario;
    private MiCuentaDAO cuenta;

    public MiCuentaBean() {
        inicializar();
    }

    private void inicializar() {
        usuario = ManejadorSesiones.getUsuario();
        cuenta = new MiCuentaDAO(usuario.getConexion());
        cambiarAvatar = new PopUpCambiarAvatar(usuario, cuenta);

    }

    public void limpiar() {
        inicializar();
    }

    public PopUpCambiarAvatar getCambiarAvatar() {
        return cambiarAvatar;
    }

    public void modificarDatosUsuario() {
        System.out.println("Se modifico el usuario");

    }

    public UsuarioTO getUsuario() {
        return usuario;
    }

}
