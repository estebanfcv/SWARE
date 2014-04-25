package com.pan.sware.TO;

/**
 *
 * @author estebanfcv
 */
public class PerfilTO {

    private String nombre;
    private boolean miCuenta = false;
    private boolean coordinaciones = false;
    private boolean parametrosGlobales = false;
    private boolean agenda = false;
    private boolean campania = false;
    private long tiempoSesion = 60000;

    public long getTiempoSesion() {
        return tiempoSesion;
    }

    public void setTiempoSesion(Long tiempoSesion) {
        switch (tiempoSesion.intValue()) {
            case 7200000:
                this.tiempoSesion = 2;
                break;
            case 14400000:
                this.tiempoSesion = 4;
                break;
            case 28800000:
                this.tiempoSesion = 8;
                break;
            default:
                this.tiempoSesion = tiempoSesion;
                break;
        }
    }

    public boolean isMiCuenta() {
        return miCuenta;
    }

    public void setMiCuenta(boolean miCuenta) {
        this.miCuenta = miCuenta;
    }

    public void setMiCuenta(int miCuenta) {
        this.miCuenta = miCuenta == 1;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isCoordinaciones() {
        return coordinaciones;
    }

    public void setCoordinaciones(boolean coordinaciones) {
        this.coordinaciones = coordinaciones;
    }

    public void setCoordinaciones(int coordinaciones) {
        this.coordinaciones = coordinaciones == 1;
    }

    public boolean isParametrosGlobales() {
        return parametrosGlobales;
    }

    public void setParametrosGlobales(boolean parametrosGlobales) {
        this.parametrosGlobales = parametrosGlobales;
    }

    public void setParametrosGlobales(int parametrosGlobales) {
        this.parametrosGlobales = parametrosGlobales == 1;
    }

    public boolean isAgenda() {
        return agenda;
    }

    public void setAgenda(int agenda) {
        this.agenda = agenda == 1;
    }

    public boolean isCampania() {
        return campania;
    }

    public void setCampania(int campania) {
        this.campania = campania == 1;
    }

}
