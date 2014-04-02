package com.pan.sware.correo;

import com.pan.sware.TO.UsuarioTO;
import java.util.Date;

/**
 *
 * @author estebanfcv
 */
public class CuerpoCorreos {

    public static void enviarCorreoAltaUsuario(UsuarioTO usuario) {
        String subject = "Recuperar password - " + usuario.getUsername();
        String texto = "A continuacion tiene sus credenciales:"
                + "<br>"
                + "<hr>"
                + "Nombre de usuario:  " + usuario.getUsername() + "<br> "
                + "Contrasena: " + usuario.getPassword() + "<br> "
                + "<hr>"
                + "<br> Emitida el " + new Date();

        String html = "<html><body><b>" + usuario.getNombre() + ":</b><br/><br>" + texto + "</body> </html>";
        enviarCorreo(
                subject, //asunto
                html, //texto
                usuario.getEmail(), //to 
                null, //cc
                "SWARE recuperar contraseña");   //motivo
    }

    public static void enviarCorreo(
            String asunto,
            String texto,
            String to,
            String cc,
            String motivo) {
//        asunto = reemplazarCaracteresEspeciales(asunto);
//        texto = reemplazarCaracteresEspeciales(texto);
        try {
            MailTask task = null;
            String[] datosCorreo = new String[5];
            datosCorreo[0] = "estebanfcv@gmail.com";
            datosCorreo[1] = "estebanfcv090.";
            datosCorreo[2] = "smtp.gmail.com";
            datosCorreo[3] = "587";
            datosCorreo[4] = "1";

            task = new MailTask("0", //idEmpresa
                    to, //String to
                    cc, //String cc
                    asunto, //String subject
                    texto, //String text
                    datosCorreo, //String[] datosCorreo
                    null);  //attachment
            new Thread(task).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CuerpoCorreos() {
    }
}
