package com.pan.sware.catalogos;

import com.pan.sware.Queries.CatCoordinaciones;
import com.pan.sware.TO.CoordinacionTO;
import com.pan.sware.Util.Constantes;
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
public class CoordinacionDAO {

    private Connection conexion;

    public CoordinacionDAO() {
        conexion = ManejadorSesiones.getUsuario().getConexion();
    }

    public List<CoordinacionTO> obtenerListaCoordinaciones() {
        List<CoordinacionTO> listaCoordinaciones = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(CatCoordinaciones.CONSULTAR_COORDINACIONES);
            rs = ps.executeQuery();
            while (rs.next()) {
                CoordinacionTO c = new CoordinacionTO();
                c.setId(rs.getInt("ID"));
                c.setNombre(rs.getString("NOMBRE"));
                c.setNombreResponsable(rs.getString("NOMBRE_RESPONSABLE"));
                c.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
                c.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
                c.setCalle(rs.getString("CALLE"));
                c.setNumeroExterior(rs.getString("NUMERO_EXTERIOR"));
                c.setNumeroInterior(rs.getString("NUMERO_INTERIOR"));
                c.setColonia(rs.getString("COLONIA"));
                c.setCodigoPostal(rs.getString("CODIGO_POSTAL"));
                c.setTelefono(rs.getString("TELEFONO"));
                c.setEmail(rs.getString("EMAIL"));
                c.setFechaAlta(rs.getTimestamp("FECHA_ALTA"));
                c.setAvatar(rs.getBytes("AVATAR"));
                c.setUsername(rs.getString("USERNAME"));
                listaCoordinaciones.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
        }
        return listaCoordinaciones;
    }

    public boolean insertarCoordinacion(CoordinacionTO coordinacion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exito = false;
        try {
            conexion.commit();
            ps = conexion.prepareStatement(CatCoordinaciones.INSERTAR_COORDINACION, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, coordinacion.getNombre());
            ps.setString(2, coordinacion.getNombreResponsable());
            ps.setString(3, coordinacion.getApellidoPaterno());
            ps.setString(4, coordinacion.getApellidoMaterno());
            ps.setString(5, coordinacion.getCalle());
            ps.setString(6, coordinacion.getNumeroExterior());
            ps.setString(7, coordinacion.getNumeroInterior());
            ps.setString(8, coordinacion.getColonia());
            ps.setString(9, coordinacion.getCodigoPostal());
            ps.setString(10, coordinacion.getTelefono());
            ps.setString(11, coordinacion.getEmail());
            ps.setTimestamp(12, new Timestamp(new Date().getTime()));
            ps.setBytes(13, coordinacion.getAvatar());
            ps.setString(14, coordinacion.getUsername());
            coordinacion.setPassword(Util.generarPasswordAleatorio());
            ps.setString(15, Util.encryptMD5(coordinacion.getPassword()));
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                coordinacion.setId(rs.getInt(1));
            }
            exito = mandarCorreo(coordinacion);
        } catch (Exception e) {
            ConnectionUtil.rollBack(conexion, "CoordinacionDAO.insertarCoordinacion()");
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
        }
        return exito;
    }

    private boolean mandarCorreo(CoordinacionTO coordinacion) {
        boolean exito = false;
        try {
            if (CuerpoCorreos.enviarCorreoAltaCoordinacion(coordinacion)) {
                conexion.commit();
                exito = true;
            } else {
                ConnectionUtil.rollBack(conexion, "CoordinacionDAO.mandarCorreo()");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exito;
    }

    public boolean modificarCoordinacion(CoordinacionTO coordinacion) {
        boolean exito = false;
        PreparedStatement ps = null;
        try {
            conexion.commit();
            ps = conexion.prepareStatement(CatCoordinaciones.MODIFICAR_COORDINACION);
            ps.setString(1, coordinacion.getNombre());
            ps.setString(2, coordinacion.getNombreResponsable());
            ps.setString(3, coordinacion.getApellidoPaterno());
            ps.setString(4, coordinacion.getApellidoMaterno());
            ps.setString(5, coordinacion.getCalle());
            ps.setString(6, coordinacion.getNumeroExterior());
            ps.setString(7, coordinacion.getNumeroInterior());
            ps.setString(8, coordinacion.getColonia());
            ps.setString(9, coordinacion.getCodigoPostal());
            ps.setString(10, coordinacion.getTelefono());
            ps.setBytes(11, coordinacion.getAvatar());
            ps.setInt(12, coordinacion.getId());
            ps.executeUpdate();
             conexion.commit();
            exito = true;
        } catch (Exception e) {
            ConnectionUtil.rollBack(conexion, "CoordinacionDAO.modificarCoordinacion()");
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
        }
        return exito;
    }

    public boolean eliminarCoordinacion(CoordinacionTO coordinacion) {
        boolean exito = false;
        PreparedStatement ps = null;
        try {
            conexion.commit();
            ps = conexion.prepareStatement(CatCoordinaciones.ELIMINAR_COORDINACION);
            ps.setInt(1, coordinacion.getId());
            ps.executeUpdate();
            exito = true;
            conexion.commit();
        } catch (Exception e) {
            ConnectionUtil.rollBack(conexion, "CoordinacionDAO.eliminarCoordinacion()");
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
        }
        return exito;
    }

}
