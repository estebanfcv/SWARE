package com.pan.sware.catalogos.perfiles;

import com.pan.sware.sesiones.ManejadorSesiones;
import java.sql.Connection;

/**
 *
 * @author estebanfcv
 */
public class PerfilDAO {
    
    Connection conexion;

    public PerfilDAO() {
        conexion=ManejadorSesiones.getUsuario().getConexion();
    }
    
    
    
}
