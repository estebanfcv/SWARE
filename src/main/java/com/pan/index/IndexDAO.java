package com.pan.index;

import com.pan.sware.Queries.Index;
import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.Util.Util;
import com.pan.sware.correo.CuerpoCorreos;
import com.pan.sware.db.ConnectionUtil;
import com.pan.sware.db.DBConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author estebanfcv
 */
public class IndexDAO {

    public UsuarioTO obtenerUsuarioPorUsernameOEmail(String busqueda, int tipoBusqueda) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String condicion;
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
                u.setId(rs.getInt("ID"));
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

    public boolean modificarPassword(UsuarioTO usuario) {
        boolean exito = false;
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = DBConnectionManager.getInstance().getConnection(DBConnectionManager.BD);
            con.setAutoCommit(false);
            con.commit();
            ps = con.prepareStatement(Index.MODIFICAR_PASSWORD);
            ps.setString(1, Util.encryptMD5(usuario.getPassword()));
            ps.setInt(2, usuario.getId());
            ps.executeUpdate();
            exito = mandarCorreo(usuario, con);
        } catch (Exception e) {
            e.printStackTrace();
            ConnectionUtil.rollBack(con, "IndexDAO.modificarPassword()");
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
            ConnectionUtil.cerrarConexiones(con);
        }
        return exito;
    }

    private boolean mandarCorreo(UsuarioTO usuario, Connection con) {
        boolean exito = false;
        try {
            if (CuerpoCorreos.enviarCorreoRecuperarPassword(usuario)) {
                con.commit();
                exito = true;
            } else {
                ConnectionUtil.rollBack(con, "IndexDAO.mandarCorreo()");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exito;
    }
}
