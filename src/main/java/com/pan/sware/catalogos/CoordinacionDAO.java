package com.pan.sware.catalogos;

import com.pan.sware.Queries.CatCoordinaciones;
import com.pan.sware.TO.CoordinacionMunicipioTO;
import com.pan.sware.TO.CoordinacionTO;
import com.pan.sware.db.ConnectionUtil;
import com.pan.sware.sesiones.ManejadorSesiones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author estebanfcv
 */
public class CoordinacionDAO {

    Connection conexion;

    public CoordinacionDAO() {
        this.conexion = ManejadorSesiones.getUsuario().getConexion();
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
                c.setCodigoPostal(rs.getInt("CODIGO_POSTAL"));
                c.setTelefono(rs.getString("TELEFONO"));
                c.setEmail(rs.getString("EMAIL"));
                c.setFechaAlta(rs.getTimestamp("FECHA_ALTA"));
                c.setAvatar(rs.getBytes("AVATAR"));
                c.setListaMunicipios(consultarListaCoordinacionMunicipios(c.getId()));
                listaCoordinaciones.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
        }
        return listaCoordinaciones;
    }

    private List<CoordinacionMunicipioTO> consultarListaCoordinacionMunicipios(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CoordinacionMunicipioTO> listaCoordinacionMunicipio = new ArrayList<>();
        try {
            ps = conexion.prepareStatement(CatCoordinaciones.CONSULTAR_MUNICIPIOS_COORDINACION);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                CoordinacionMunicipioTO cm = new CoordinacionMunicipioTO();
                cm.setIdCoordinacion(rs.getInt("ID_COORDINACION"));
                cm.setIdEstado(rs.getByte("ID_ESTADO"));
                cm.setIdMunicipio(rs.getShort("ID_MUNICIPIO"));
                listaCoordinacionMunicipio.add(cm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
        }

        return listaCoordinacionMunicipio;
    }

    public boolean insertarCoordinacion(CoordinacionTO coordinacion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exito = false;

        try {
            conexion.setAutoCommit(false);
            conexion.commit();
            ps = conexion.prepareStatement(CatCoordinaciones.INSERTAR_COORDINACION,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, coordinacion.getNombre());
            ps.setString(2, coordinacion.getNombreResponsable());
            ps.setString(3, coordinacion.getApellidoPaterno());
            ps.setString(4, coordinacion.getApellidoMaterno());
            ps.setString(5, coordinacion.getCalle());
            ps.setString(6, coordinacion.getNumeroExterior());
            ps.setString(7, coordinacion.getNumeroInterior());
            ps.setString(8, coordinacion.getColonia());
            ps.setInt(9, coordinacion.getCodigoPostal());
            ps.setString(10, coordinacion.getTelefono());
            ps.setString(11, coordinacion.getEmail());
            ps.setTimestamp(12, new Timestamp(new Date().getTime()));
            ps.setBytes(13, coordinacion.getAvatar());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                coordinacion.setId(rs.getInt(1));
                System.out.println("el id de coordinacion es:::::: " + coordinacion.getId());
                exito = true;
            }
            if (exito) {
                for (CoordinacionMunicipioTO cm : coordinacion.getListaMunicipios()) {
                    cm.setIdCoordinacion(coordinacion.getId());
                }
                if (insertarCoordinacionMunicipio(coordinacion.getListaMunicipios())) {
                    conexion.commit();
                } else {
                    exito = false;
                    conexion.rollback();
                    System.out.println("se deshace todas las operaciones");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
        }
        return exito;
    }

    public boolean modificarCoordinacion(CoordinacionTO coordinacion) {
        boolean exito = false;
        PreparedStatement ps = null;
        try {
            conexion.setAutoCommit(false);
            conexion.commit();
            if (eliminarMunicipioCoordinacion(coordinacion)) {
                ps = conexion.prepareStatement(CatCoordinaciones.MODIFICAR_COORDINACION);
                ps.setString(1, coordinacion.getNombre());
                ps.setString(2, coordinacion.getNombreResponsable());
                ps.setString(3, coordinacion.getApellidoPaterno());
                ps.setString(4, coordinacion.getApellidoMaterno());
                ps.setString(5, coordinacion.getCalle());
                ps.setString(6, coordinacion.getNumeroExterior());
                ps.setString(7, coordinacion.getNumeroInterior());
                ps.setString(8, coordinacion.getColonia());
                ps.setInt(9, coordinacion.getCodigoPostal());
                ps.setString(10, coordinacion.getTelefono());
                ps.setString(11, coordinacion.getEmail());
                ps.setBytes(12, coordinacion.getAvatar());
                ps.setInt(13, coordinacion.getId());
                ps.executeUpdate();
                exito = true;
                if (insertarCoordinacionMunicipio(coordinacion.getListaMunicipios())) {
                    conexion.commit();
                } else {
                    exito = false;
                    conexion.rollback();
                    System.out.println("se deshace todas las operaciones");
                }
            }
        } catch (Exception e) {
            try {
                conexion.rollback();
            } catch (Exception ex) {
                System.out.println("se deshace todas las operaciones");
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
        }

        return exito;

    }

    private boolean insertarCoordinacionMunicipio(List<CoordinacionMunicipioTO> listaCM) {
        boolean exito = false;
        PreparedStatement ps = null;
        try {

            ps = conexion.prepareStatement(CatCoordinaciones.INSERTAR_COORDINACION_MUNICIPIOS);
            for (CoordinacionMunicipioTO cm : listaCM) {
                ps.setInt(1, cm.getIdCoordinacion());
                ps.setByte(2, cm.getIdEstado());
                ps.setShort(3, cm.getIdMunicipio());
                ps.addBatch();
            }
            ps.executeBatch();
            exito = true;

        } catch (Exception e) {
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
            conexion.setAutoCommit(false);
            conexion.commit();
            ps = conexion.prepareStatement(CatCoordinaciones.ELIMINAR_COORDINACION);
            ps.setInt(1, coordinacion.getId());
            ps.executeUpdate();
            exito = true;
            if (exito) {
                if (eliminarMunicipioCoordinacion(coordinacion)) {
                    conexion.commit();
                } else {
                    conexion.rollback();
                    exito = false;
                    System.out.println("se deshace todas las operaciones");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
        }
        return exito;
    }

    private boolean eliminarMunicipioCoordinacion(CoordinacionTO coordinacion) {
        boolean exito = false;
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(CatCoordinaciones.ELIMINAR_MUNICIPIOS_COORDINACION);
            ps.setInt(1, coordinacion.getId());
            ps.executeUpdate();
            exito = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
        }
        return exito;
    }
}
