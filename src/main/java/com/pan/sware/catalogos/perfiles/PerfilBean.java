package com.pan.sware.catalogos.perfiles;

import com.pan.sware.TO.PerfilTO;
import com.pan.sware.Util.Constantes;
import com.pan.sware.Util.Util;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author estebanfcv
 */
@ManagedBean(name = "perf")
@ViewScoped
public class PerfilBean implements Serializable{

    private PerfilTO perfil;
    private List<PerfilTO> listaPerfiles;
    private List<SelectItem> listaTiempo;
    private String mensajeError;
    private String color;
    private String mensajeBoton;

    public PerfilBean() {
        mensajeError = "";
        color = "color: green";
        mensajeBoton = Constantes.BOTON_AGREGAR;
        inicializar();

    }

    private void inicializar() {
        perfil = new PerfilTO();
        listaTiempo = Constantes.armarListaTiempoSesion();
        consultar();

    }

    private void consultar() {
        mensajeError = "";
        color = "color: green";
    }
   


    public void actionAgregarModificar() {
        System.out.println("df");

    }

    public void limpiar() {
        mensajeError = "";
        color = "color: green";
        inicializar();

    }

    public PerfilTO getPerfil() {
        return perfil;
    }

    public List<PerfilTO> getListaPerfiles() {
        return listaPerfiles;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public String getColor() {
        return color;
    }

    public List<SelectItem> getListaTiempo() {
        return listaTiempo;
    }

    public String getMensajeBoton() {
        return mensajeBoton;
    }
}
