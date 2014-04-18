package com.pan.sware.cuenta;

import com.pan.sware.Queries.MiCuenta;
import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.db.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author estebanfcv
 */
public class MiCuentaDAO {
    
    Connection conexion;

    public MiCuentaDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public boolean actualizarAvatarUsuario(UsuarioTO usuario){
        boolean exito=false;
        PreparedStatement ps =null;
        try {
            ps=conexion.prepareStatement(MiCuenta.ACTUALIZAR_AVATAR);
            ps.setBytes(1, usuario.getAvatar());
            ps.setInt(2, usuario.getId());
            ps.executeUpdate();
            exito=true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
             ConnectionUtil.cerrarConexiones(null, ps);
        }
        return exito;
    }
    
    

    
    
    
}
