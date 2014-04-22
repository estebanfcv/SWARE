package com.pan.sware.agenda;

import com.pan.sware.TO.AgendaTO;
import com.pan.sware.Util.Constantes;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
    private String prueba
            = "<p>\n"
            + "	<strong>DSADAS</strong></p>\n"
            + "<p>\n"
            + "	&nbsp;</p>\n"
            + "<p>\n"
            + "	<em>SDASD</em></p>\n"
            + "<p>\n"
            + "	&nbsp;</p>\n"
            + "<ol>\n"
            + "	<li>\n"
            + "		D</li>\n"
            + "	<li>\n"
            + "		F</li>\n"
            + "	<li>\n"
            + "		G</li>\n"
            + "</ol>\n"
            + "<ul>\n"
            + "	<li>\n"
            + "		DS</li>\n"
            + "</ul>\n"
            + "";

    public AgendaBean() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    private void inicializar() {
        agendaEventos = new AgendaTO();
        listaHoras = Constantes.ArmarListaHoras();
        agendaEventos.setHora(listaHoras.get(0).getValue().toString());
        listaMinutos = Constantes.ArmarListaMinutos();
        agendaEventos.setMinutos(listaMinutos.get(0).getValue().toString());
        mensajeBoton = Constantes.BOTON_AGREGAR;

    }

    public void actionAgregarModificar() {
        System.out.println(" mensaje  " + agendaEventos.getMensaje());
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

    public String getPrueba() {
        return prueba;
    }
    
    
}
