package com.pan.sware.main;

import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.Util.ParametroCache;
import com.pan.sware.Util.Util;
import com.pan.sware.catalogos.usuarios.UsuariosDAO;
import com.pan.sware.sesiones.ManejadorSesiones;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author estebanfcv
 */
public class popUpCambioPassword {

    private boolean popUp;
    private String mensajeError;
    private String color;
    private String passwordActual;
    private String passwordNuevo;
    private String passwordConfirmacion;
    private UsuarioTO usuario;
    private UsuariosDAO dao;

    public popUpCambioPassword(UsuarioTO usuario) {
        System.out.println("entre aqui");
        this.usuario = usuario;
        mensajeError = "";
        color = "color: green";
        inicializar();

    }

    private void inicializar() {
        dao = new UsuariosDAO();
        passwordActual = "";
        passwordNuevo = "";
        passwordConfirmacion = "";
        System.out.println("el usuario random es:::::: " + usuario.getPasswordRandom());
        popUp = usuario.getPasswordRandom() == 1;
    }

    public String cambiarPassword() throws NoSuchAlgorithmException, CloneNotSupportedException {
        mensajeError = "";
        if (validarCamposPassword()) {
            if (validarPassWordNuevo()) {
                usuario.setPassword(Util.encryptMD5(passwordNuevo));
                usuario.setPasswordRandom((byte) 0);
                if (dao.modificarPassword(usuario)) {
                    ParametroCache.inicializarUsuarios();
                    ManejadorSesiones.eliminarSesion();
                    return "loginCerrarSesion";
                } else {
                    mensajeError = "No se pudo modificar su password";
                }
            }
        }
        return "";
    }

    private boolean validarCamposPassword() throws NoSuchAlgorithmException, CloneNotSupportedException {
        color = "color: red";
        System.out.println("el password actual es:::: "+passwordActual);
        if (passwordActual.isEmpty()) {
            mensajeError = "Favor de escribir su password actual";
            return false;
        } else {
            if (ParametroCache.obtenerUsuarioTOPorUserPass(usuario.getUsername(), Util.encryptMD5(passwordActual)) == null) {
                mensajeError = "El password actual es incorrecto";
                return false;
            }
        }
        if (passwordNuevo.isEmpty()) {
            mensajeError = "Favor de escribir su nuevo password";
            return false;
        }
        if (passwordConfirmacion.isEmpty()) {
            mensajeError = "Favor de confirmar su nuevo password";
            return false;
        } else {
            if (!passwordNuevo.equals(passwordConfirmacion)) {
                mensajeError = "Los passwords son diferentes";
            }
        }
        return true;
    }

    private boolean validarPassWordNuevo() {
        Pattern pattern = Pattern.compile("(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*");
        Matcher matcher = pattern.matcher(passwordNuevo);
        if (8 > passwordNuevo.length()) {
            mensajeError = "EL password es muy corto";
            return false;
        }
        if (!matcher.matches()) {
            mensajeError = "El password que ingreso debe contener al menos un número, una letra minúscula y una mayúscula."
                    + " No debe contener caracteres especiales";
            return false;
        }
        return true;
    }

    public void cerrarPopUp() {
        popUp = false;
    }

    public boolean isPopUp() {
        return popUp;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public String getColor() {
        return color;
    }

    public UsuarioTO getUsuario() {
        return usuario;
    }

    public String getPasswordActual() {
        return passwordActual;
    }

    public void setPasswordActual(String passwordActual) {
        this.passwordActual = passwordActual;
    }

    public String getPasswordNuevo() {
        return passwordNuevo;
    }

    public void setPasswordNuevo(String passwordNuevo) {
        this.passwordNuevo = passwordNuevo;
    }

    public String getPasswordConfirmacion() {
        return passwordConfirmacion;
    }

    public void setPasswordConfirmacion(String passwordConfirmacion) {
        this.passwordConfirmacion = passwordConfirmacion;
    }
}
