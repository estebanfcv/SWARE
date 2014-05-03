package com.pan.index;

import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.Util.ParametroCache;
import com.pan.sware.Util.Util;
import com.pan.sware.correo.CuerpoCorreos;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
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

    public void botonAceptar() throws NoSuchAlgorithmException, CloneNotSupportedException {
        if (validarCamposVacios()) {
            usuario = ind.obtenerUsuarioPorUsernameOEmail(usernameEmail, opcionUserEmail);
            if (usuario != null) {
                usuario.setPassword(Util.generarPasswordAleatorio());
                System.out.println("el password es::::: "+usuario.getPassword());
                if (ind.modificarPassword(usuario)) {
                    mensaje = "se enviará un correo a " + usuario.getEmail() + " con los datos para poder accesar";
                    color = "color: green";
                    ParametroCache.inicializarUsuarios();
                    inicializar();
                }else{
                  mensaje = "Hubo un error";
                color = "color: red";  
                }
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
            if (!Util.validarEmail(usernameEmail)) {
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
        mensaje = "";
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
