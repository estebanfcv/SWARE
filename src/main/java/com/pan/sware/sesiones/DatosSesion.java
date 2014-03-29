package com.pan.sware.sesiones;


import com.pan.sware.TO.UsuarioTO;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Cesar Villegas
 */
public class DatosSesion {
    
    private Map<String, UsuarioTO> usuariosRegistrados;
    private static DatosSesion datosSesion;

    static {
        datosSesion = new DatosSesion();
    }

    private DatosSesion() {
        usuariosRegistrados = new HashMap<>();
    }

    public static DatosSesion getInstance() {
        return datosSesion;
    }

    public Map<String, UsuarioTO> getUsuariosRegistrados() {
        return usuariosRegistrados;
    }


}
