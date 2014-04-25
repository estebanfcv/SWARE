package com.pan.sware.db;

import com.pan.sware.TO.UsuarioTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author estebanfcv
 */
public class ConnectionUtil {

    public static void endConnection(UsuarioTO usuario) {
        try {

            if (null != usuario.getConexion()) {
                usuario.getConexion().close();
                DBConnectionManager.conexionesTotalesAbiertasLectura--;
                System.out.println("Cerrar conexiones lectura..."
                        + DBConnectionManager.conexionesTotalesAbiertasLectura + " : " + usuario.getUsername());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cerrarConexiones(ResultSet rs, PreparedStatement ps) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cerrarConexiones(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rollBack(Connection con, String nombreMetodo) {
        try {
            System.out.println("se deshacen los cambios: " + nombreMetodo);
            con.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
