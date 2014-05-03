package com.pan.sware.catalogos.usuarios;

import com.pan.sware.Queries.CatUsuarios;
import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.Util.Util;
import com.pan.sware.correo.CuerpoCorreos;
import com.pan.sware.db.ConnectionUtil;
import com.pan.sware.sesiones.ManejadorSesiones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author estebanfcv
 */
public class UsuariosDAO {

    private Connection conexion;

    public UsuariosDAO() {
        conexion = ManejadorSesiones.getUsuario().getConexion();
    }

    public List<UsuarioTO> obtenerListaUsuarios() {
        List<UsuarioTO> listaUsuarios = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(CatUsuarios.CONSULTAR_USUARIOS);
            ps.setInt(1, ManejadorSesiones.getUsuario().getIdCoordinacion());
            rs = ps.executeQuery();
            while (rs.next()) {
                UsuarioTO u = new UsuarioTO();
                u.setId(rs.getInt("ID"));
                u.setUsername(rs.getString("USERNAME"));
                u.setPassword(rs.getString("PASSWORD"));
                u.setEmail(rs.getString("EMAIL"));
                u.setAvatar(rs.getBytes("AVATAR"));
                u.setNombre(rs.getString("NOMBRE"));
                u.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
                u.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
                u.setIdPerfil(rs.getInt("ID_PERFIL"));
                u.setTelefonoCasa(rs.getString("TELEFONO_CASA"));
                u.setTelefonoCelular(rs.getString("TELEFONO_CELULAR"));
                u.setTelefonoTrabajo(rs.getString("TELEFONO_TRABAJO"));
                u.setBloqueado((rs.getInt("BLOQUEADO") == 1));
                listaUsuarios.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
        }
        return listaUsuarios;
    }

    public boolean insertarUsuario(UsuarioTO usuario) {
        boolean exito = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conexion.commit();
            ps = conexion.prepareStatement(CatUsuarios.INSERTAR_USUARIO);
            ps.setString(1, usuario.getUsername());
            usuario.setPassword(Util.generarPasswordAleatorio());
            ps.setString(2, Util.encryptMD5(usuario.getPassword()));
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getNombre());
            ps.setString(5, usuario.getApellidoPaterno());
            ps.setString(6, usuario.getApellidoMaterno());
            ps.setInt(7, usuario.getIdPerfil());
            ps.setString(8, usuario.getTelefonoCasa());
            ps.setString(9, usuario.getTelefonoTrabajo());
            ps.setString(10, usuario.getTelefonoCelular());
            ps.setInt(11, usuario.isBloqueado() ? 1 : 0);
            ps.setTimestamp(12, new Timestamp(new Date().getTime()));
            ps.setInt(13, ManejadorSesiones.getUsuario().getIdCoordinacion());
            ps.setByte(14, (byte) 1);
            ps.executeUpdate();
            exito = mandarCorreo(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            ConnectionUtil.rollBack(conexion, "UsuariosDAO.insertarUsuario()");
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
        }
        return exito;
    }

    private boolean mandarCorreo(UsuarioTO usuario) {
        boolean exito = false;
        try {
            if (CuerpoCorreos.enviarCorreoAltaUsuario(usuario)) {
                conexion.commit();
                exito = true;
            } else {
                ConnectionUtil.rollBack(conexion, "UsuariosDAO.mandarCorreo()");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exito;
    }

    public boolean modificarUsuario(UsuarioTO usuario) {
        boolean exito = false;
        PreparedStatement ps = null;
        try {
            conexion.commit();
            ps = conexion.prepareStatement(CatUsuarios.MODIFICAR_USUARIO);
            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellidoPaterno());
            ps.setString(4, usuario.getApellidoMaterno());
            ps.setInt(5, usuario.getIdPerfil());
            ps.setString(6, usuario.getTelefonoCasa());
            ps.setString(7, usuario.getTelefonoTrabajo());
            ps.setString(8, usuario.getTelefonoCelular());
            ps.setInt(9, usuario.isBloqueado() ? 1 : 0);
            ps.setInt(10, usuario.getId());
            ps.executeUpdate();
            conexion.commit();
            exito = true;
        } catch (Exception e) {
            e.printStackTrace();
            ConnectionUtil.rollBack(conexion, "UsuariosDAO.modificarUsuario()");
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
        }
        return exito;
    }

    public boolean eliminarUsuario(UsuarioTO usuario) {
        boolean exito = false;
        PreparedStatement ps = null;
        try {
            conexion.commit();
            ps = conexion.prepareStatement(CatUsuarios.ELIMINAR_USUARIO);
            ps.setInt(1, usuario.getId());
            ps.executeUpdate();
            conexion.commit();
        } catch (Exception e) {
            ConnectionUtil.rollBack(conexion, "UsuariosDAO.eliminarUsuario()");
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
        }
        return exito;
    }

    public boolean modificarPassword(UsuarioTO usuario) {
        boolean exito = false;
        PreparedStatement ps = null;
        try {
            conexion.commit();
            ps=conexion.prepareStatement(CatUsuarios.MODIFICAR_PASSWORD);
            ps.setString(1, usuario.getPassword());
            ps.setInt(2, usuario.getId());
            ps.executeUpdate();
            conexion.commit();
            exito=true;
        } catch (Exception e) {
            e.printStackTrace();
            ConnectionUtil.rollBack(conexion, "UsuariosDAO.eliminarUsuario()");
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
        }
        return exito;
    }
}
