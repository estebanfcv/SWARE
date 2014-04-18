package com.pan.sware.Util;

import com.pan.sware.Queries.Index;
import com.pan.sware.TO.EstadoTO;
import com.pan.sware.TO.MunicipioTO;
import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.db.ConnectionUtil;
import com.pan.sware.db.DBConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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
