package com.pan.index;

import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.Util.Util;
import com.pan.sware.correo.CuerpoCorreos;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

public class RecuperarPassword {

    private List<SelectItem> comboOpciones;
    private String color;
    private IndexDAO ind;
    private int opcionUserEmail;
    private boolean popUp;
    private String usernameEmail;
    private String mensaje;
    private UsuarioTO usuario;

    public RecuperarPassword() {
        mensaje = "";
        color = "color: green";
        inicializar();
    }

    private void inicializar() {
        usernameEmail = "";
        armarComboOpciones();
        opcionUserEmail = 1;
        ind = new IndexDAO();
    }

    private void armarComboOpciones() {
        comboOpciones = new ArrayList<>();
        comboOpciones.add(new SelectItem(1, "Username"));
        comboOpciones.add(new SelectItem(2, "E-mail"));

    }

    public String getSeleccionarPanel() {
        if (opcionUserEmail == 1) {
            return "username";
        } else {
            return "email";
        }
    }

    public void valueChangeOpciones(ValueChangeEvent event) {
        if (Util.isUpdatePhase(event)) {
            mensaje = "";
            usernameEmail = "";
        }
    }

    public void botonAceptar() {
        if (validarCamposVacios()) {
            usuario = ind.obtenerUusuarioPorUsernameOEmail(usernameEmail, opcionUserEmail);
            if (usuario != null) {
                // enviar correo
                CuerpoCorreos.enviarCorreoAltaUsuario(usuario);
                mensaje = "se enviara un correo a " + usuario.getEmail() + " con los datos para poder accesar";
                color = "color: green";
                inicializar();
            } else {
                mensaje = "no exite un usuario con esos datos";
                color = "color: red";
            }
        }

    }

    public boolean validarCamposVacios() {
        if (usernameEmail.isEmpty()) {
            String complemento = opcionUserEmail == 1 ? "username" : "email";
            mensaje = "Favor de escribir el " + complemento;
            color = "color: red";
            return false;
        }

        if (opcionUserEmail == 2) {
            Pattern patternEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = patternEmail.matcher(usernameEmail);
            if (!matcher.matches()) {
                mensaje = "El e-mail no es valido";
                color = "color: red";
                return false;
            }
        }
        return true;
    }

    public void abrirPopUp() {
        popUp = true;
    }

    public void cerrarPopUp() {
        popUp = false;
    }

    public String getColor() {
        return color;
    }

    public int getOpcionUserEmail() {
        return opcionUserEmail;
    }

    public boolean isPopUp() {
        return popUp;
    }

    public void setOpcionUserEmail(int opcionUserEmail) {
        this.opcionUserEmail = opcionUserEmail;
    }

    public List<SelectItem> getComboOpciones() {
        return comboOpciones;
    }

    public String getUsernameEmail() {
        return usernameEmail;
    }

    public void setUsernameEmail(String usernameEmail) {
        this.usernameEmail = usernameEmail;
    }

    public String getMensaje() {
        return mensaje;
    }

}
