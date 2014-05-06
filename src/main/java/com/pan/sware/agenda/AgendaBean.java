package com.pan.sware.agenda;

import com.pan.sware.TO.AgendaTO;
import com.pan.sware.Util.Constantes;
import com.pan.sware.Util.ParametroCache;
import com.pan.sware.Util.Util;
import com.pan.sware.sesiones.ManejadorSesiones;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author estebanfcv
 */
@ManagedBean(name = "agenda")
@ViewScoped
public class AgendaBean implements Serializable {

    private AgendaTO agendaEventos;
    private String mensajeError;
    private String color;
    private String mensajeBoton;
    private List<SelectItem> listaHoras;
    private List<SelectItem> listaMinutos;
    private List<AgendaTO> listaAgenda;
    private AgendaDAO daoAgenda;
    private boolean tablaVisible;
    private int filas;
    private boolean popUpEliminar;
    private Date fechaFiltro;

    public AgendaBean() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    private void inicializar() {
        daoAgenda = new AgendaDAO();
        agendaEventos = new AgendaTO();
        listaHoras = Constantes.armarListaHoras();
        agendaEventos.setHoras(listaHoras.get(0).getValue().toString());
        listaMinutos = Constantes.ArmarListaMinutos();
        agendaEventos.setMinutos(listaMinutos.get(0).getValue().toString());
        mensajeBoton = Constantes.BOTON_AGREGAR;
        tablaVisible = false;
        filas = 20;
        popUpEliminar = false;
        fechaFiltro = new Date();
        consultar();
    }

    private void consultar() {
        listaAgenda = daoAgenda.obtenerAgenda(fechaFiltro);
        if (!listaAgenda.isEmpty()) {
            tablaVisible = true;
        } else {
            tablaVisible = false;
            mensajeError = "No hay nada Agendado para hoy.";
            color = "color: red";
        }
    }

    public void valueChangeFecha(ValueChangeEvent event) {
        if (Util.isUpdatePhase(event)) {
            mensajeError = "";
            color = "color: green";
            consultar();
        }
    }

    public void actionAgregarModificar() {
        if (validarCamposObligatorios()) {
            if (mensajeBoton.equals(Constantes.BOTON_AGREGAR)) {
                if (validarDatos()) {
                    if (daoAgenda.insertarAgenda(agendaEventos)) {
                        mensajeError = "Inserción Exitosa";
                        color = "color: green";
                        ParametroCache.inicializarAgenda();
                        inicializar();
                    } else {
                        mensajeError = "La inserción no se pudo realizar";
                        color = "color: red";
                    }
                }
            } else {
                if (validarModificaciones()) {
                    if (daoAgenda.modificarAgenda(agendaEventos)) {
                        mensajeError = "La agenda se modificó exitosamente";
                        color = "color: green";
                        ParametroCache.inicializarAgenda();
                        inicializar();
                    } else {
                        mensajeError = "La agenda no se pudo modificar";
                        color = "color: red";
                    }
                }
            }
        }
    }

    private boolean validarCamposObligatorios() {
        if (agendaEventos.getTitulo().isEmpty()) {
            mensajeError = "Favor de escribir el titulo";
            color = "color: red";
            return false;
        }
        if (agendaEventos.getMensaje().isEmpty()) {
            mensajeError = "Favor de escribir el mensaje";
            color = "color: red";
            return false;
        }
        return true;
    }

    private boolean validarDatos() {
        if (Util.obtenerSoloFecha(agendaEventos.getFecha()).compareTo(Util.obtenerSoloFecha(new Date())) < 0) {
            mensajeError = "No se pueden seleccionar dias anteriores a hoy";
            color = "color: red";
            return false;
        }
        for (AgendaTO a : listaAgenda) {
            if (a.getTitulo().equals(agendaEventos.getTitulo())) {
                mensajeError = "El titulo ya existe";
                color = "color: red";
                return false;
            }
            if (a.getMensaje().equals(agendaEventos.getMensaje())) {
                mensajeError = "El mensaje ya existe";
                color = "color: red";
                return false;
            }
        }
        return true;
    }

    private boolean validarModificaciones() {
        if (agendaEventos.getIdUsuario() != ManejadorSesiones.getUsuario().getId()) {
            mensajeError = "No se pueden modificar mensajes ajenos";
            color = "color: red";
            return false;
        }
        if (Util.obtenerSoloFecha(agendaEventos.getFecha()).compareTo(Util.obtenerSoloFecha(new Date())) < 0) {
            mensajeError = "No se pueden seleccionar dias anteriores a hoy";
            color = "color: red";
            return false;
        }
        for (AgendaTO a : listaAgenda) {
            if (a.getId() == agendaEventos.getId()) {
                continue;
            }
            if (a.getTitulo().equals(agendaEventos.getTitulo())) {
                mensajeError = "El titulo ya existe";
                color = "color: red";
                return false;
            }
            if (a.getMensaje().equals(agendaEventos.getMensaje())) {
                mensajeError = "El mensaje ya existe";
                color = "color: red";
                return false;
            }
        }
        return true;
    }

    public void actionListenerModificar(ActionEvent event) {
        agendaEventos = ((AgendaTO) event.getComponent().getAttributes().get("ag")).clone();
        System.out.println("21 " + agendaEventos.getFecha());
        mensajeError = "";
        mensajeBoton = Constantes.BOTON_MODIFICAR;
    }

    public void abrirPopUpEliminar(ActionEvent event) {
        agendaEventos = (AgendaTO) event.getComponent().getAttributes().get("ag");
        popUpEliminar = true;
    }

    public void confirmarEliminarEvento() {
        if (daoAgenda.eliminarAgenda(agendaEventos)) {
            mensajeError = "EL evento se eliminó con éxito";
            color = "color: green";
            ParametroCache.inicializarAgenda();
            inicializar();
        } else {
            mensajeError = "El evento no se pudo eliminar";
            color = "color: red";
        }
        popUpEliminar = false;
    }

    public void cerrarPopUpEliminar() {
        popUpEliminar = false;
    }

    public void limpiar() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    public AgendaTO getAgendaEventos() {
        return agendaEventos;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public String getColor() {
        return color;
    }

    public List<SelectItem> getListaHoras() {
        return listaHoras;
    }

    public List<SelectItem> getListaMinutos() {
        return listaMinutos;
    }

    public String getMensajeBoton() {
        return mensajeBoton;
    }

    public List<AgendaTO> getListaAgenda() {
        return listaAgenda;
    }

    public boolean isTablaVisible() {
        return tablaVisible;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public boolean isPopUpEliminar() {
        return popUpEliminar;
    }

    public Date getFechaFiltro() {
        return fechaFiltro;
    }

    public void setFechaFiltro(Date fechaFiltro) {
        this.fechaFiltro = fechaFiltro;
    }

    public String obtenerNombreAutor(int id) {
        return ParametroCache.obtenerUsuarioTOPorId(id).getNombre();
    }
}