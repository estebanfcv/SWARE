package com.pan.sware.Util;

import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author estebanfcv
 */
public class Constantes {

    public static final String BOTON_AGREGAR = "Agregar";
    public static final String BOTON_MODIFICAR = "Modificar";
    public static final String IMAGEN_DEFAULT = "/imagenes/sinImagen.jpg";
    public static final int TAMANIO_ARCHIVO = 921600;
    public static final byte VALOR_LISTA_VACIA = -1;

    public static List<SelectItem> armarListaHoras() {
        List<SelectItem> listaHoras = new ArrayList<>();
        listaHoras.add(new SelectItem("0"));
        listaHoras.add(new SelectItem("1"));
        listaHoras.add(new SelectItem("2"));
        listaHoras.add(new SelectItem("3"));
        listaHoras.add(new SelectItem("4"));
        listaHoras.add(new SelectItem("5"));
        listaHoras.add(new SelectItem("6"));
        listaHoras.add(new SelectItem("7"));
        listaHoras.add(new SelectItem("8"));
        listaHoras.add(new SelectItem("9"));
        listaHoras.add(new SelectItem("10"));
        listaHoras.add(new SelectItem("11"));
        listaHoras.add(new SelectItem("12"));
        listaHoras.add(new SelectItem("13"));
        listaHoras.add(new SelectItem("14"));
        listaHoras.add(new SelectItem("15"));
        listaHoras.add(new SelectItem("16"));
        listaHoras.add(new SelectItem("17"));
        listaHoras.add(new SelectItem("18"));
        listaHoras.add(new SelectItem("19"));
        listaHoras.add(new SelectItem("20"));
        listaHoras.add(new SelectItem("21"));
        listaHoras.add(new SelectItem("22"));
        listaHoras.add(new SelectItem("23"));
        return listaHoras;
    }

    public static List<SelectItem> armarListaTiempoSesion() {
        List<SelectItem> tiempo = new ArrayList<>();
        tiempo.add(new SelectItem(7200000L));
        tiempo.add(new SelectItem(14400000L));
        tiempo.add(new SelectItem(28800000L));
        return tiempo;
    }

    public static List<SelectItem> ArmarListaMinutos() {
        List<SelectItem> listaMinutos = new ArrayList<>();
        listaMinutos.add(new SelectItem("0"));
        listaMinutos.add(new SelectItem("1"));
        listaMinutos.add(new SelectItem("2"));
        listaMinutos.add(new SelectItem("3"));
        listaMinutos.add(new SelectItem("4"));
        listaMinutos.add(new SelectItem("5"));
        listaMinutos.add(new SelectItem("6"));
        listaMinutos.add(new SelectItem("7"));
        listaMinutos.add(new SelectItem("8"));
        listaMinutos.add(new SelectItem("9"));
        listaMinutos.add(new SelectItem("10"));
        listaMinutos.add(new SelectItem("11"));
        listaMinutos.add(new SelectItem("12"));
        listaMinutos.add(new SelectItem("13"));
        listaMinutos.add(new SelectItem("14"));
        listaMinutos.add(new SelectItem("15"));
        listaMinutos.add(new SelectItem("16"));
        listaMinutos.add(new SelectItem("17"));
        listaMinutos.add(new SelectItem("18"));
        listaMinutos.add(new SelectItem("19"));
        listaMinutos.add(new SelectItem("20"));
        listaMinutos.add(new SelectItem("21"));
        listaMinutos.add(new SelectItem("22"));
        listaMinutos.add(new SelectItem("23"));
        listaMinutos.add(new SelectItem("24"));
        listaMinutos.add(new SelectItem("25"));
        listaMinutos.add(new SelectItem("26"));
        listaMinutos.add(new SelectItem("27"));
        listaMinutos.add(new SelectItem("28"));
        listaMinutos.add(new SelectItem("29"));
        listaMinutos.add(new SelectItem("30"));
        listaMinutos.add(new SelectItem("31"));
        listaMinutos.add(new SelectItem("32"));
        listaMinutos.add(new SelectItem("33"));
        listaMinutos.add(new SelectItem("34"));
        listaMinutos.add(new SelectItem("35"));
        listaMinutos.add(new SelectItem("36"));
        listaMinutos.add(new SelectItem("37"));
        listaMinutos.add(new SelectItem("38"));
        listaMinutos.add(new SelectItem("39"));
        listaMinutos.add(new SelectItem("40"));
        listaMinutos.add(new SelectItem("41"));
        listaMinutos.add(new SelectItem("42"));
        listaMinutos.add(new SelectItem("43"));
        listaMinutos.add(new SelectItem("44"));
        listaMinutos.add(new SelectItem("45"));
        listaMinutos.add(new SelectItem("46"));
        listaMinutos.add(new SelectItem("47"));
        listaMinutos.add(new SelectItem("48"));
        listaMinutos.add(new SelectItem("49"));
        listaMinutos.add(new SelectItem("50"));
        listaMinutos.add(new SelectItem("51"));
        listaMinutos.add(new SelectItem("52"));
        listaMinutos.add(new SelectItem("53"));
        listaMinutos.add(new SelectItem("54"));
        listaMinutos.add(new SelectItem("55"));
        listaMinutos.add(new SelectItem("56"));
        listaMinutos.add(new SelectItem("57"));
        listaMinutos.add(new SelectItem("58"));
        listaMinutos.add(new SelectItem("59"));
        return listaMinutos;
    }
}
