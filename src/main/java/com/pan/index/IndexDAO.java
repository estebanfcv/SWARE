package com.pan.index;

import com.pan.sware.Queries.Index;
import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.db.ConnectionUtil;
import com.pan.sware.db.DBConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author estebanfcv
 */
public class IndexDAO {

    public Map<String, UsuarioTO> obtenerListaUsuarios() {
        Map<String, UsuarioTO> listaUsuarios = new LinkedHashMap<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnectionManager.getInstance().getConnection(DBConnectionManager.BD);
            ps = con.prepareStatement(Index.CONSULTAR_USUARIOS);
            rs = ps.executeQuery();
            while (rs.next()) {
                Date f;
                f = rs.getTimestamp("FECHA_ALTA");
                System.out.println("la fecha es:::::::: " + f);
                UsuarioTO u = new UsuarioTO();
                u.setUsername(rs.getString("USERNAME"));
                u.setPassword(rs.getString("PASSWORD"));
                u.setAvatar(rs.getBytes("AVATAR"));
                listaUsuarios.put(u.getUsername() + "|" + u.getPassword(), u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
            ConnectionUtil.cerrarConexiones(con);
        }
        return listaUsuarios;
    }

    public UsuarioTO obtenerUusuarioPorUsernameOEmail(String busqueda, int tipoBusqueda) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String condicion;
        String email = "";
        UsuarioTO u = null;
        try {
            condicion = tipoBusqueda == 1 ? " WHERE USERNAME=?" : " WHERE EMAIL=?";
            con = DBConnectionManager.getInstance().getConnection(DBConnectionManager.BD);
            ps = con.prepareStatement(Index.CONSULTAR_USUARIOS + condicion);
            ps.setString(1, busqueda);
            System.out.println("El query es:::::: " + Index.CONSULTAR_USUARIOS + condicion);
            rs = ps.executeQuery();
            if (rs.next()) {
                u = new UsuarioTO();
                u.setEmail(rs.getString("EMAIL"));
                u.setUsername(rs.getString("USERNAME"));
                u.setPassword(rs.getString("PASSWORD"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
            ConnectionUtil.cerrarConexiones(con);
        }
        return u;
    }
}
