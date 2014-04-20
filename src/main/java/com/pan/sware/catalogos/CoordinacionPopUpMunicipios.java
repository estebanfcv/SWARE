package com.pan.sware.catalogos;

import com.pan.sware.TO.CoordinacionMunicipioTO;
import com.pan.sware.TO.CoordinacionTO;
import com.pan.sware.TO.EstadoTO;
import com.pan.sware.TO.MunicipioTO;
import com.pan.sware.Util.Constantes;
import com.pan.sware.Util.ParametroCache;
import com.pan.sware.Util.Util;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author estebanfcv
 */
public class CoordinacionPopUpMunicipios {
//http://www.microrregiones.gob.mx/catloc/

    private boolean visible;
    private boolean mostrar;
    private String error;
    private String colorMensaje;
    private List<SelectItem> comboEstados;
    private List<SelectItem> comboMunicipios;
    private CoordinacionTO coordinacion;
    private CoordinacionMunicipioTO coorMun;
    private int filas;

    public CoordinacionPopUpMunicipios(CoordinacionTO coordinacion) {
        this.coordinacion = coordinacion;
        error = "";
        colorMensaje = "color: green";
        inicializar();

    }

    private void inicializar() {
        coorMun = new CoordinacionMunicipioTO();
        coorMun.setIdCoordinacion(coordinacion.getId());
        visible = false;
        armarComboEstados();
        coorMun.setIdEstado((Byte) comboEstados.get(0).getValue());
        armarComboMunicipios();
        coorMun.setIdMunicipio((Short) comboMunicipios.get(0).getValue());
        mostrar = false;
        filas = 20;
        consultar();
    }

    private void consultar() {
        if (!coordinacion.getListaMunicipios().isEmpty()) {
            mostrar = true;
        } else {
            error = "No hay municipios Asignados";
            colorMensaje = "color: red";
            mostrar = false;
        }
    }

    private void armarComboEstados() {
        comboEstados = new ArrayList<>();
        for (EstadoTO e : ParametroCache.getEstados().values()) {
            comboEstados.add(new SelectItem(e.getId(), e.getNombre()));
        }
        if (comboEstados.isEmpty()) {
            comboEstados.add(new SelectItem((byte) Constantes.VALOR_LISTA_VACIA, "No hay Estados"));
        }
    }

    private void armarComboMunicipios() {
        comboMunicipios = new ArrayList<>();
        for (MunicipioTO m : ParametroCache.getMunicipios().values()) {
            if (m.getIdEstado() == coorMun.getIdEstado()) {
                comboMunicipios.add(new SelectItem(m.getId(), m.getNombre()));
            }
        }
        if (comboMunicipios.isEmpty()) {
            comboMunicipios.add(new SelectItem((short) Constantes.VALOR_LISTA_VACIA, "No hay municipios"));
        }
    }

    public void valueChangeListenerEstados(ValueChangeEvent event) {
        error = "";
//        coorMun = new CoordinacionMunicipioTO();
        coorMun = coorMun.clone();
        if (Util.isUpdatePhase(event)) {
            coorMun.setIdEstado((Byte) event.getNewValue());
            armarComboMunicipios();
            coorMun.setIdMunicipio((Short) comboMunicipios.get(0).getValue());
        }
    }

    public void valueChangeListenerMunicipio(ValueChangeEvent event) {
        error = "";
        coorMun = coorMun.clone();
        if (Util.isUpdatePhase(event)) {
            coorMun.setIdMunicipio((Short) event.getNewValue());
        }
    }

    public void agregarMunicipio() {
        if (coorMun.getIdEstado() != -1 && coorMun.getIdMunicipio() != -1) {
            if (validarDuplicados()) {
                coordinacion.getListaMunicipios().add(coorMun);
                error = "Municipio Asignado con éxito";
                colorMensaje = "color: green";
                consultar();
            }
        }

    }

    public boolean validarDuplicados() {
        for (CoordinacionMunicipioTO cm : coordinacion.getListaMunicipios()) {
            if (cm.getIdEstado() == coorMun.getIdEstado() && cm.getIdMunicipio() == coorMun.getIdMunicipio()) {
                error = "Ese municipio ya fue asignado";
                colorMensaje = "color: red";
                return false;
            }
        }
        return true;
    }

    public void eliminarMunicipio(ActionEvent event) {
        coorMun = (CoordinacionMunicipioTO) event.getComponent().getAttributes().get("muni");
        error = coordinacion.getListaMunicipios().remove(coorMun) ? "El municipio se eliminó con éxito." : "no se pudo eliminar";
        colorMensaje = error.contains("municipio") ? "color: green" : "color: red";
        inicializar();
        visible=true;
    }

    public String obtenerNombreEstado(byte id) {
        return ParametroCache.getEstados().get(id).getNombre();
    }

    public String obtenerNombreMunicipio(short id) {
        return ParametroCache.getMunicipios().get(id).getNombre();
    }

    public void abrirPopUp() {

        visible = true;
    }

    public void cerrarPopUp() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    public String getError() {
        return error;
    }

    public String getColorMensaje() {
        return colorMensaje;
    }

    public CoordinacionTO getCordinacion() {
        return coordinacion;
    }

    public CoordinacionMunicipioTO getCoorMun() {
        return coorMun;
    }

    public List<SelectItem> getComboEstados() {
        return comboEstados;
    }

    public List<SelectItem> getComboMunicipios() {
        return comboMunicipios;
    }

    public boolean isMostrar() {
        return mostrar;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }
}
