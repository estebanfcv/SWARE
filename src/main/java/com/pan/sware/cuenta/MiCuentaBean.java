package com.pan.sware.cuenta;

import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.Util.GeneralUtil;
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
public class MiCuentaBean implements  Serializable{
    
    private PopUpCambiarAvatar cambiarAvatar = new PopUpCambiarAvatar();
    private UsuarioTO usuario = new UsuarioTO();
   

    public MiCuentaBean() {
        inicializar();
    }
    
    private void inicializar(){
        usuario=ManejadorSesiones.getUsuario();
        System.out.println("el usuario es:::: "+GeneralUtil.debugImprimirContenidoObjecto(usuario));
        
    }
    
    public void limpiar(){
        inicializar();
    }

    public PopUpCambiarAvatar getCambiarAvatar() {
        return cambiarAvatar;
    }
    
    public void modificarDatosUsuario(){
        System.out.println("Se modifico el usuario");
        
    }

    public UsuarioTO getUsuario() {
        return usuario;
    }
    
}