package com.pan.sware.agenda;

import com.pan.sware.Queries.Agenda;
import com.pan.sware.TO.AgendaTO;
import com.pan.sware.db.ConnectionUtil;
import com.pan.sware.sesiones.ManejadorSesiones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author estebanfcv
 */
public class AgendaDAO {

    Connection conexion;

    public AgendaDAO() {
        conexion = ManejadorSesiones.getUsuario().getConexion();
        

    }

    public List<AgendaTO> obtenerAgenda(AgendaTO agenda) {
        List<AgendaTO> listaAgenda = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaString = sdf.format(agenda.getFecha());
        try {
            ps = conexion.prepareStatement(Agenda.CONSULTAR_AGENDA);

            ps.setString(1, fechaString + " 00:00:00");
            ps.setString(2, fechaString + " 23:59:00");
            ps.setInt(3, ManejadorSesiones.getUsuario().getIdCoordinacion());
            rs = ps.executeQuery();
            while (rs.next()) {
                AgendaTO a = new AgendaTO();
                a.setId(rs.getInt("ID"));
                a.setTitulo(rs.getString("TITULO"));
                a.setMensaje(rs.getString("MENSAJE"));
                a.setFecha(rs.getTimestamp("FECHA"));
                Calendar horasMinutos = Calendar.getInstance();
                horasMinutos.setTime(a.getFecha());
                a.setHoras(String.valueOf(horasMinutos.get(Calendar.HOUR_OF_DAY)));
                a.setMinutos(String.valueOf(horasMinutos.get(Calendar.MINUTE)));
                a.setIdUsuario(rs.getInt("ID_USUARIO"));
                listaAgenda.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(rs, ps);
        }
        return listaAgenda;
    }

    public boolean insertarAgenda(AgendaTO agenda) {
        boolean exito = false;
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(Agenda.INSERTAR_AGENDA);
            ps.setString(1, agenda.getTitulo());
            ps.setString(2, agenda.getMensaje());
            if (agenda.getHoras().length() == 1) {
                agenda.setHoras("0" + agenda.getHoras());
            }
            if (agenda.getMinutos().length() == 1) {
                agenda.setMinutos("0" + agenda.getMinutos());
            }
            System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(agenda.getFecha()) + " " + agenda.getHoras() + ":" + agenda.getMinutos() + ":00");
            ps.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(agenda.getFecha()) + " " + agenda.getHoras() + ":" + agenda.getMinutos() + ":00");
            ps.setTimestamp(4, new Timestamp(new Date().getTime()));
            ps.setInt(5, ManejadorSesiones.getUsuario().getId());
            ps.setInt(6, ManejadorSesiones.getUsuario().getIdCoordinacion());
            ps.executeUpdate();
            conexion.commit();
            exito = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
        }
        return exito;
    }

    public boolean modificarAgenda(AgendaTO agenda) {
        boolean exito = false;
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(Agenda.MODIFICAR_AGENDA);
            ps.setString(1, agenda.getTitulo());
            ps.setString(2, agenda.getMensaje());
            if (agenda.getHoras().length() == 1) {
                agenda.setHoras("0" + agenda.getHoras());
            }
            if (agenda.getMinutos().length() == 1) {
                agenda.setMinutos("0" + agenda.getMinutos());
            }
            ps.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(agenda.getFecha()) + " " + agenda.getHoras() + ":" + agenda.getMinutos() + ":00");
            ps.setInt(4, agenda.getId());
            ps.executeUpdate();
            conexion.commit();
            exito = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
        }
        return exito;
    }

    public boolean eliminarAgenda(AgendaTO agenda) {
        boolean exito = false;
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(Agenda.ELIMINAR_AGENDA);
            ps.setInt(1, agenda.getId());
            ps.executeUpdate();
            conexion.commit();
            exito = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.cerrarConexiones(null, ps);
        }

        return exito;
    }

}
