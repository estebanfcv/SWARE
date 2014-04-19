package com.pan.sware.correo;

import com.pan.sware.TO.CoordinacionTO;
import com.pan.sware.TO.UsuarioTO;
import java.util.Date;

/**
 *
 * @author estebanfcv
 */
public class CuerpoCorreos {

    public static boolean enviarCorreoAltaCoordinacion(CoordinacionTO coordinacion) {
        String subject = "Bienvenido al sistema SWARE";
        String texto = " A continuación tiene sus credenciales para acceder al sistema"
                + "<br>"
                + "<hr>"
                + "Username:  " + coordinacion.getUsername() + "<br> "
                + "Password: " + coordinacion.getPassword() + "<br> "
                + "<hr>"
                + "<br> Emitida el " + new Date();
        String contenido = "<html><body><b>" + coordinacion.getNombre() + ":</b><br/><br>" + texto + "</body> </html>";
        return enviarCorreo(subject,contenido,coordinacion.getEmail(),null);
    }

    public static void enviarCorreoAltaUsuario(UsuarioTO usuario) {
        String subject = "Recuperar password - " + usuario.getUsername();
        String texto = "A continuacion tiene sus credenciales:"
                + "<br>"
                + "<hr>"
                + "Nombre de usuario:  " + usuario.getUsername() + "<br> "
                + "Contrasena: " + usuario.getPassword() + "<br> "
                + "<hr>"
                + "<br> Emitida el " + new Date();

        String contenido = "<html><body><b>" + usuario.getNombre() + ":</b><br/><br>" + texto + "</body> </html>";
        enviarCorreo(subject,
                contenido,
                usuario.getEmail(), //to 
                null); //cc   
    }

    public static boolean enviarCorreo(String asunto, String texto, String to, String cc) {
        boolean envioExitoso = false;
        try {
            MailTask task = null;
            String[] datosCorreo = new String[5];
            datosCorreo[0] = "estebanfcv@gmail.com";
            datosCorreo[1] = "estebanfcv090";
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
            envioExitoso = true;
        } catch (Exception e) {
            envioExitoso = false;
            e.printStackTrace();
        }
        return envioExitoso;
    }

    private CuerpoCorreos() {
    }
}
