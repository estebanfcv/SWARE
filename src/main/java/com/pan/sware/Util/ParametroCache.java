package com.pan.sware.Util;

import com.pan.sware.Queries.Index;
import com.pan.sware.TO.CoordinacionMunicipioTO;
import com.pan.sware.TO.CoordinacionTO;
import com.pan.sware.TO.EstadoTO;
import com.pan.sware.TO.MunicipioTO;
import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.db.ConnectionUtil;
import com.pan.sware.db.DBConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author estebanfcv
 */
@SuppressWarnings({"UnusedAssignment", "UseSpecificCatch"})
public class ParametroCache {

    public ParametroCache() {
    }

    private static Map<Integer, CoordinacionTO> coordinaciones = new LinkedHashMap<>();
    private static Map<String, UsuarioTO> usuarios = new LinkedHashMap<>();
    private static Map<Byte, EstadoTO> estados = new LinkedHashMap<>();
    private static Map<Short, MunicipioTO> municipios = new LinkedHashMap<>();

    public static void inicializarEstados() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnectionManager.getInstance().getConnection(DBConnectionManager.BD);
            ps = con.prepareStatement(Index.CONSULTAR_ESTADOS);
            rs = ps.executeQuery();
            while (rs.next()) {
                EstadoTO e = new EstadoTO();
                e.setId(rs.getByte("ID"));
                e.setNombre(rs.getString("NOMBRE"));
                estados.put(e.getId(), e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
            ConnectionUtil.cerrarConexiones(con);
        }
    }

    public static void inicializarMunicipios() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnectionManager.getInstance().getConnection(DBConnectionManager.BD);
            ps = con.prepareStatement(Index.CONSULTAR_MUNICIPIOS);
            rs = ps.executeQuery();
            while (rs.next()) {
                MunicipioTO m = new MunicipioTO();
                m.setId(rs.getShort("ID"));
                m.setNombre(rs.getString("NOMBRE"));
                m.setIdEstado(rs.getByte("ID_ESTADO"));
                municipios.put(m.getId(), m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
            ConnectionUtil.cerrarConexiones(con);
        }
    }

    public static void inicializarCoordinaciones() {
        Map<Integer, CoordinacionTO> mapA = new LinkedHashMap<>(coordinaciones);
        Map<Integer, CoordinacionTO> mapB = new LinkedHashMap<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnectionManager.getInstance().getConnection(DBConnectionManager.BD);
            ps = con.prepareStatement(Index.CONSULTAR_COORDINACIONES);
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
                c.setPassword(rs.getString("PASSWORD"));
                c.setUsername(rs.getString("USERNAME"));
                c.setListaMunicipios(consultarListaCoordinacionMunicipios(con, c.getId()));
                mapB.put(c.getId(), c);
            }
            coordinaciones.putAll(mapB);

            Set<Integer> keysInA = new LinkedHashSet<>(mapA.keySet());
            Set<Integer> keysInB = new LinkedHashSet<>(mapB.keySet());
            if (!keysInA.equals(keysInB)) {
                Set<Integer> inANotB = new LinkedHashSet<>(keysInA);
                inANotB.removeAll(keysInB);
                for (Integer i : inANotB) {
                    coordinaciones.remove(i);
                }
                inANotB = null;
            }
            mapA = null;
            mapB = null;
            keysInA = null;
            keysInB = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
            ConnectionUtil.cerrarConexiones(con);
        }
    }
    
    private static List<CoordinacionMunicipioTO> consultarListaCoordinacionMunicipios(Connection con,int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CoordinacionMunicipioTO> listaCoordinacionMunicipio = new ArrayList<>();
        try {
            ps = con.prepareStatement(Index.CONSULTAR_MUNICIPIOS_COORDINACION);
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

    public static void inicializarUsuarios() {
        System.out.println("Inicializando usuarios...");
        Map<String, UsuarioTO> mapA = new LinkedHashMap<>(usuarios);
        Map<String, UsuarioTO> mapB = new LinkedHashMap<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnectionManager.getInstance().getConnection(DBConnectionManager.BD);
            ps = con.prepareStatement(Index.CONSULTAR_USUARIOS);
            rs = ps.executeQuery();
            while (rs.next()) {
                UsuarioTO u = new UsuarioTO();
                u.setId(rs.getInt("ID"));
                u.setUsername(rs.getString("USERNAME"));
                u.setPassword(rs.getString("PASSWORD"));
                u.setAvatar(rs.getBytes("AVATAR"));
                u.setEmail(rs.getString("EMAIL"));
                u.setNombre(rs.getString("NOMBRE"));
                u.setApellidoPaterno(rs.getString("APELLIDO_PATERNO"));
                u.setApellidoMaterno(rs.getString("APELLIDO_MATERNO"));
                u.setFechaAlta(rs.getTimestamp("FECHA_ALTA"));
                u.setIdPerfil(rs.getInt("ID_PERFIL"));
                u.setIdCoordinacion(rs.getInt("ID_COORDINACION"));
                u.setTelefonoCasa(rs.getString("TELEFONO_CASA"));
                u.setTelefonoOficina(rs.getString("TELEFONO_OFICINA"));
                u.setTelefonoCelular(rs.getString("TELEFONO_CELULAR"));
                mapB.put(new StringBuilder(u.getUsername()).append('|').append(u.getPassword()).toString(), u);
            }

            usuarios.putAll(mapB);
            Set<String> keysInA = new LinkedHashSet<>(mapA.keySet());
            Set<String> keysInB = new LinkedHashSet<>(mapB.keySet());
            if (!keysInA.equals(keysInB)) {
                Set<String> inANotB = new LinkedHashSet<>(keysInA);
                inANotB.removeAll(keysInB);
                for (String s : inANotB) {
                    usuarios.remove(s);
                }
                inANotB = null;
            }
            mapA = null;
            mapB = null;
            keysInA = null;
            keysInB = null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
            ConnectionUtil.cerrarConexiones(con);
        }
    }

    public static CoordinacionTO isUsuarioCoordinacion(String user, String pass) {
        for (CoordinacionTO c : coordinaciones.values()) {
            if (c.getUsername().equals(user) && c.getPassword().equals(pass)) {
                return c;
            }
        }
        return null;
    }

    public static UsuarioTO obtenerUsuarioTOPorUserPass(String user, String pass) {
        return usuarios.get((new StringBuilder(user).append('|').append(pass)).toString());
    }

    public static UsuarioTO obtenerUsuarioTOPorId(int id) {
        for (UsuarioTO u : usuarios.values()) {
            if (id == u.getId()) {
                return u;
            }
        }
        return null;
    }

    public static Map<String, UsuarioTO> getUsuarios() {
        return usuarios;
    }

    public static Map<Byte, EstadoTO> getEstados() {
        return estados;
    }

    public static Map<Short, MunicipioTO> getMunicipios() {
        return municipios;
    }

}
