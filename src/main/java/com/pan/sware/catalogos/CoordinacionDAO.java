package com.pan.sware.catalogos;

import com.pan.sware.Queries.CatCoordinaciones;
import com.pan.sware.TO.CoordinacionMunicipioTO;
import com.pan.sware.TO.CoordinacionTO;
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
            rs=ps.executeQuery();
            while(rs.next()){
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

}
