package com.pan.sware.main;

import com.icesoft.faces.component.menubar.MenuItem;
import com.pan.sware.TO.AgendaTO;
import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.Util.ParametroCache;
import com.pan.sware.Util.Util;
import com.pan.sware.agenda.AgendaDAO;
import com.pan.sware.sesiones.ManejadorSesiones;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author estebanfcv
 */
@ManagedBean(name = "main")
@ViewScoped
public class MainBean implements Serializable {

    private Collection menuSuperior;
    private Collection menu;
    private Collection regresarMain;
    private UsuarioTO usuario;
    //
    private PopUpEvento evento;
    private AgendaTO agendaEventos;
    private String mensajeError;
    private String color;
    private List<AgendaTO> listaAgenda;
    private AgendaDAO daoAgenda;
    private boolean tablaVisible;
    private int filas;
    private Date fechaFiltro;

    public MainBean() {
        System.out.println(TimeZone.getDefault().getDisplayName());
        usuario = ManejadorSesiones.getUsuario();
        ArmarMenus();
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    private void inicializar() {
        evento = new PopUpEvento();
        daoAgenda = new AgendaDAO();
        agendaEventos = new AgendaTO();
        filas = 20;
        fechaFiltro = new Date();
        tablaVisible = false;
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

    public String getMensajeError() {
        return mensajeError;
    }

    public String getColor() {
        return color;
    }

    public List<AgendaTO> getListaAgenda() {
        return listaAgenda;
    }

    public boolean isTablaVisible() {
        return tablaVisible;
    }

    public Date getFechaFiltro() {
        return fechaFiltro;
    }

    public void setFechaFiltro(Date fechaFiltro) {
        this.fechaFiltro = fechaFiltro;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public String obtenerNombreAutor(int id) {
        return ParametroCache.obtenerUsuarioTOPorId(id).getNombre();
    }

    public PopUpEvento getEvento() {
        return evento;
    }
    //

    private void ArmarMenus() {
        mostrarMenuSuperior();
        mostrarMenu();
        mostrarMenuRegresarMain();

    }

    private void mostrarMenuSuperior() {
        menuSuperior = new ArrayList();
        menuMiCuenta();
        cerrarSesion();

    }

    private void mostrarMenu() {
        menu = new ArrayList();
        menuCatalogos();
        menuAgenda();

    }

    private void mostrarMenuRegresarMain() {
        regresarMain = new ArrayList();
        regresar();
    }

    private void menuMiCuenta() {

        if (usuario.getPerfil().isMiCuenta()) {
            MenuItem miCuenta = new MenuItem();
            miCuenta.setValue("Mi Cuenta");
            miCuenta.setLink("/SWARE-1/MiCuenta/MiCuenta.xhtml");
            menuSuperior.add(miCuenta);
        }

    }

    public String eliminarSesion() {
        ManejadorSesiones.eliminarSesion();
        return "loginCerrarSesion";
    }

    private void cerrarSesion() {
        MenuItem cerrar = new MenuItem();
        cerrar.setValue("Cerrar Sesión");
        cerrar.setAction(FacesContext.getCurrentInstance().getApplication().createMethodBinding("#{main.eliminarSesion}",
                new Class[]{}));
        menuSuperior.add(cerrar);
    }

    private void menuCatalogos() {
        if (usuario.getPerfil().isCoordinaciones() || usuario.getPerfil().isCampania()) {
            MenuItem catalogos = new MenuItem();
            catalogos.setValue("Catálogos");
            if (usuario.getPerfil().isCoordinaciones()) {
                MenuItem coordinaciones = new MenuItem();
                coordinaciones.setValue("Coordinaciones");
                coordinaciones.setLink("/SWARE-1/Catalogos/Coordinaciones.xhtml");
                coordinaciones.setIcon("/imagenes/iconos/calendario.png");
                catalogos.getChildren().add(coordinaciones);
            }
            if (usuario.getPerfil().isCampania()) {
                MenuItem campania = new MenuItem();
                campania.setValue("Campañas");
                campania.setLink("/SWARE-1/Catalogos/Campania.xhtml");
                catalogos.getChildren().add(campania);
                
            }
            menu.add(catalogos);
        }
    }

    private void menuAgenda() {
        if (usuario.getPerfil().isAgenda()) {
            MenuItem agenda = new MenuItem();
            agenda.setValue("Agenda");
            agenda.setLink("/SWARE-1/Agenda/Agenda.xhtml");
            menu.add(agenda);
        }
    }

    private void regresar() {
        MenuItem regresar = new MenuItem();
        regresar.setValue("Regresar a la página principal");
        regresar.setLink("/SWARE-1/Main.xhtml");
        regresarMain.add(regresar);
    }

    public Collection getMenu() {
        return menu;
    }

    public Collection getMenuSuperior() {
        return menuSuperior;
    }

    public Collection getRegresarMain() {
        return regresarMain;
    }

}
