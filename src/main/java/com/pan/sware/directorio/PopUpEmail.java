package com.pan.sware.directorio;

import com.pan.sware.correo.CuerpoCorreos;

/**
 *
 * @author estebanfcv
 */
public class PopUpEmail {

    private String listaCorreos;
    private boolean visible;
    private String asunto;
    private String mensaje;
    private String mensajeError;
    private String color;

    public PopUpEmail() {
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    private void inicializar() {
        asunto = "";
        mensaje = "";
        visible = false;
    }

    public void enviarCorreo() {
        mensajeError = "";
        color = "color: green";
        if (validarCamposObligatorios()) {
            if (CuerpoCorreos.enviarCorreoDirectorio(this)) {
                mensajeError = "El Email se envió con éxito";
            } else {
                mensajeError = "No se pudo mandar el Email";
            }
            color = mensajeError.contains("éxito") ? "color: green" : "color: red";
        }
    }

    private boolean validarCamposObligatorios() {
        color = "color: red";
        if (asunto.isEmpty()) {
            mensajeError = "Favor de escribir el asunto del Email";
            return false;
        }
        if (mensaje.isEmpty()) {
            mensajeError = "Favor de escribir el mensaje del Email";
            return false;
        }
        return true;
    }

    public void abrirPopUp() {
        System.out.println("A");
        visible = true;
        System.out.println("B");
    }

    public void cerrarPopup() {
        System.out.println("C");
        visible = false;
    }

    public String getListaCorreos() {
        return listaCorreos;
    }

    public void setListaCorreos(String listaCorreos) {
        this.listaCorreos = listaCorreos;
    }

    public boolean isVisible() {
        return visible;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public String getColor() {
        return color;
    }

    public String getAsunto() {
        return asunto.trim();
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje.trim();
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}