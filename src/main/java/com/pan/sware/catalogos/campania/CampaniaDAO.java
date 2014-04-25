package com.pan.sware.catalogos.campania;

import com.pan.sware.Queries.CatCampanias;
import com.pan.sware.TO.CampaniaMunicipioTO;
import com.pan.sware.TO.CampaniaTO;
import com.pan.sware.db.ConnectionUtil;
import com.pan.sware.sesiones.ManejadorSesiones;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author estebanfcv
 */
public class CampaniaDAO {

    Connection conexion;

    public CampaniaDAO() {
        conexion = ManejadorSesiones.getUsuario().getConexion();
    }

    public List<CampaniaTO> obtenerListaCampanias() {
        List<CampaniaTO> listaCampanias = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(CatCampanias.CONSULTAR_CAMPANIAS);
            rs = ps.executeQuery();
            while (rs.next()) {
                CampaniaTO c = new CampaniaTO();
                c.setId(rs.getInt("ID"));
                c.setNombre(rs.getString("NOMBRE"));
                c.setComentario(rs.getString("COMENTARIO"));
                c.setFecha(rs.getTimestamp("FECHA"));
                c.setIdCoordinacion(rs.getInt("ID_COORDINACION"));
                c.setListaMunicipios(consultarListaCoordinacionMunicipios(c.getId()));
                c.setAvatar(rs.getBytes("AVATAR"));
                listaCampanias.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
        }
        return listaCampanias;
    }

    private List<CampaniaMunicipioTO> consultarListaCoordinacionMunicipios(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CampaniaMunicipioTO> listaCoordinacionMunicipio = new ArrayList<>();
        try {
            ps = conexion.prepareStatement(CatCampanias.CONSULTAR_MUNICIPIOS_CAMPANIA);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                CampaniaMunicipioTO cm = new CampaniaMunicipioTO();
                cm.setIdCoordinacion(rs.getInt("ID_COORDINACION"));
                cm.setIdEstado(rs.getByte("ID_ESTADO"));
                cm.setIdMunicipio(rs.getShort("ID_MUNICIPIO"));
                cm.setIdCampania(rs.getInt("ID_CAMPANIA"));
                listaCoordinacionMunicipio.add(cm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
        }

        return listaCoordinacionMunicipio;
    }

    public boolean insertarCampania(CampaniaTO campania) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exito = false;
        try {
            conexion.commit();
            ps = conexion.prepareStatement(CatCampanias.INSERTAR_CAMPANIA, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, campania.getNombre());
            ps.setString(2, campania.getComentario());
            System.out.println("la fecha insert:::: "+campania.getFecha());
            ps.setDate(3, new Date(campania.getFecha().getTime()));
            ps.setTimestamp(4, new Timestamp(new java.util.Date().getTime()));
            ps.setInt(5, ManejadorSesiones.getUsuario().getIdCoordinacion());
            ps.setBytes(6, campania.getAvatar());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                campania.setId(rs.getInt(1));
            }
            for (CampaniaMunicipioTO cm : campania.getListaMunicipios()) {
                cm.setIdCampania(campania.getId());
            }
            exito = insertarCampaniaMunicipio(campania);
        } catch (Exception e) {
            ConnectionUtil.rollBack(conexion, "CampaniaDAO.insertarCampania()");
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
        }
        return exito;
    }

    private boolean insertarCampaniaMunicipio(CampaniaTO campania) {
        boolean exito = false;
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(CatCampanias.INSERTAR_CAMPANIA_MUNICIPIOS);
            System.out.println("EL query es:::: "+CatCampanias.INSERTAR_CAMPANIA_MUNICIPIOS);
            for (CampaniaMunicipioTO cm : campania.getListaMunicipios()) {
                System.out.println("coordinacion "+cm.getIdCoordinacion());
                System.out.println("getIdCampania "+cm.getIdCampania());
                System.out.println("getIdEstado "+cm.getIdEstado());
                System.out.println("getIdMunicipio "+cm.getIdMunicipio());
                ps.setInt(1, cm.getIdCoordinacion());
                ps.setInt(2, cm.getIdCampania());
                ps.setByte(3, cm.getIdEstado());
                ps.setShort(4, cm.getIdMunicipio());
                ps.addBatch();
            }
            ps.executeBatch();
            conexion.commit();
            exito = true;
        } catch (Exception e) {
            ConnectionUtil.rollBack(conexion, "CampaniaDAO.insertarCampaniaMunicipio()");
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
        }
        return exito;
    }

    public boolean modificarCampania(CampaniaTO campania) {
        boolean exito = false;
        PreparedStatement ps = null;
        try {
            conexion.commit();
            if (eliminarMunicipioCampania(campania, "Primero")) {
                ps = conexion.prepareStatement(CatCampanias.MODIFICAR_CAMPANIA);
                ps.setString(1, campania.getNombre());
                ps.setString(2, campania.getComentario());
                 System.out.println("la fecha update:::: "+campania.getFecha());
                ps.setDate(3, new Date(campania.getFecha().getTime()));
                ps.setBytes(4, campania.getAvatar());
                ps.setInt(5, campania.getId());
                ps.executeUpdate();
                exito = insertarCampaniaMunicipio(campania);
            }
        } catch (Exception e) {
            ConnectionUtil.rollBack(conexion, "CampaniaDAO.modificarCampania()");
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
        }
        return exito;
    }

    public boolean eliminarCampania(CampaniaTO campania) {
        boolean exito = false;
        PreparedStatement ps = null;
        try {
            conexion.commit();
            ps = conexion.prepareStatement(CatCampanias.ELMINAR_CAMPANIA);
            ps.setInt(1, campania.getId());
            ps.executeUpdate();
            exito=eliminarMunicipioCampania(campania, "Ultimo");
        } catch (Exception e) {
            ConnectionUtil.rollBack(conexion, "CampaniaDAO.eliminarCampania()");
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
        }

        return exito;
    }

    private boolean eliminarMunicipioCampania(CampaniaTO campania, String numeroQuery) {
        boolean exito = false;
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(CatCampanias.ELIMINAR_MUNICIPIOS_CAMPANIA);
            ps.setInt(1, campania.getId());
            ps.executeUpdate();
            if (numeroQuery.equals("Ultimo")) {
                conexion.commit();
            }
            exito = true;
        } catch (Exception e) {
            ConnectionUtil.rollBack(conexion, "CampaniaDAO.eliminarMunicipioCampania()");
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
        }
        return exito;
    }

}
