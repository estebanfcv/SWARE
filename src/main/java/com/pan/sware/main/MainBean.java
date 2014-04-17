package com.pan.sware.main;

import com.icesoft.faces.component.menubar.MenuItem;
import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.sesiones.ManejadorSesiones;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author estebanfcv
 */
@ManagedBean(name = "main")
@ViewScoped
public class MainBean implements Serializable {

    private Collection menuSuperior;
    private Collection menu;
    private UsuarioTO usuario;

    public MainBean() {
        usuario = ManejadorSesiones.getUsuario();
        mostrarMenuSuperior();
        mostrarMenu();

    }

    private void mostrarMenuSuperior() {
        menuSuperior = new ArrayList();
        menuMiCuenta();
        cerrarSesion();

    }

    private void mostrarMenu() {
        menu = new ArrayList();
        menuCoordinaciones();

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
        cerrar.setAction(FacesContext.getCurrentInstance().getApplication().createMethodBinding("#{main.eliminarSesion}", new Class[]{}));
        menuSuperior.add(cerrar);
    }

    private void menuCoordinaciones() {
        if (usuario.getPerfil().isCoordinaciones()) {
            MenuItem coordinaciones = new MenuItem();
            coordinaciones.setValue("Coordinaciones");
            coordinaciones.setLink("/SWARE-1/Coordinaciones/Coordinaciones.xhtml");
            menu.add(coordinaciones);
        }
    }

    public Collection getMenu() {
        return menu;
    }

    public Collection getMenuSuperior() {
        return menuSuperior;
    }

}
