package com.pan.sware.directorio;

import com.pan.sware.Queries.Directorio;
import com.pan.sware.TO.DirectorioTO;
import com.pan.sware.db.ConnectionUtil;
import com.pan.sware.sesiones.ManejadorSesiones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author estebanfcv
 */
public class DirectorioDAO {

    private Connection conexion;

    public DirectorioDAO() {
        conexion = ManejadorSesiones.getUsuario().getConexion();
    }

    public List<DirectorioTO> obtenerDirectorio() {
        List<DirectorioTO> directorio = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(Directorio.CONSULTAR_DIRECTORIO);
            ps.setInt(1, ManejadorSesiones.getUsuario().getIdCoordinacion());
            rs = ps.executeQuery();
            while (rs.next()) {
                DirectorioTO dir = new DirectorioTO();
                dir.setId(rs.getInt("ID"));
                dir.setNombre(rs.getString("NOMBRE"));
                dir.setTelefono1(rs.getString("TELEFONO1"));
                dir.setTelefono2(rs.getString("TELEFONO2"));
                dir.setTelefono3(rs.getString("TELEFONO3"));
                dir.setEmail(rs.getString("EMAIL"));
                dir.setComentario(rs.getString("COMENTARIO"));
                dir.setIdCoordinacion(rs.getInt("ID_COORDINACION"));
                directorio.add(dir);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
        }
        return directorio;
    }

    public boolean insertarRegistro(DirectorioTO directorio) {
        boolean exito = false;
        PreparedStatement ps = null;
        try {
            conexion.commit();
            ps = conexion.prepareStatement(Directorio.INSERTAR_DIRECTORIO);
            ps.setString(1, directorio.getNombre());
            ps.setString(2, directorio.getTelefono1());
            ps.setString(3, directorio.getTelefono2());
            ps.setString(4, directorio.getTelefono3());
            ps.setString(5, directorio.getEmail());
            ps.setString(6, directorio.getComentario());
            ps.setInt(7, ManejadorSesiones.getUsuario().getIdCoordinacion());
            ps.executeUpdate();
            conexion.commit();
            exito = true;
        } catch (Exception e) {
            ConnectionUtil.rollBack(conexion, "DirectorioDA0.insertarRegistro");
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
        }
        return exito;
    }

    public boolean modificarRegistro(DirectorioTO directorio) {
        boolean exito = false;
        PreparedStatement ps = null;
        try {
            conexion.commit();
            ps = conexion.prepareStatement(Directorio.MODIFICAR_DIRECTORIO);
            ps.setString(1, directorio.getNombre());
            ps.setString(2, directorio.getTelefono1());
            ps.setString(3, directorio.getTelefono2());
            ps.setString(4, directorio.getTelefono3());
            ps.setString(5, directorio.getEmail());
            ps.setString(6, directorio.getComentario());
            ps.setInt(7, directorio.getId());
            ps.executeUpdate();
            conexion.commit();
            exito = true;
        } catch (Exception e) {
            ConnectionUtil.rollBack(conexion, "DirectorioDA0.modificarRegistro");
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
        }
        return exito;
    }
    
    public boolean eliminarRegistro(DirectorioTO directorio){
        boolean exito=false;
        PreparedStatement ps = null;
        try {
            conexion.commit();
            ps = conexion.prepareStatement(Directorio.ELIMINAR_DIRECTORIO);
            ps.setInt(1, directorio.getId());
            ps.executeUpdate();
            conexion.commit();
            exito=true;
        } catch (Exception e) {
             ConnectionUtil.rollBack(conexion, "DirectorioDA0.eliminarRegistro");
            e.printStackTrace();
        }finally{
            ConnectionUtil.cerrarConexiones(null, ps);
        }
        return exito;
    }
}
