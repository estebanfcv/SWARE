package com.pan.sware.cuenta;

import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.Util.Constantes;
import com.pan.sware.Util.ParametroCache;
import com.pan.sware.Util.Util;
import com.pan.sware.sesiones.ManejadorSesiones;
import java.io.File;
import java.util.StringTokenizer;
import org.icefaces.ace.component.fileentry.*;
import org.icefaces.ace.component.fileentry.FileEntryResults.FileInfo;

/**
 *
 * @author estebanfcv
 */
public class PopUpCambiarAvatar {

    private boolean popUp;
    private File file;
    private String extension;
    private String mensajeError;
    private String color;
    private UsuarioTO usuario;
    private MiCuentaDAO cuenta;

    public PopUpCambiarAvatar(UsuarioTO usuario, MiCuentaDAO cuenta) {
        this.usuario = usuario;
        this.cuenta = cuenta;
        mensajeError = "";
        color = "color: green";
        inicializar();
    }

    private void inicializar() {
        file = null;
        popUp = false;
    }

    public void sampleListener(FileEntryEvent e) {
        try {
            file = null;
            FileInfo fileInfo = ((FileEntry) e.getComponent()).getResults().getFiles().get(0);
            extension = ".";
            StringTokenizer token = new StringTokenizer(fileInfo.getContentType(), "/");
            while (token.hasMoreTokens()) {
                token.nextToken();
                extension += token.nextToken();
            }
            if (fileInfo.isSaved()) {
                if (Util.archivosPermitidos(extension)) {
                    file = fileInfo.getFile();
                    usuario.setAvatar(Util.convertirFileABytes(file));
                    fileInfo = null;
                    modificarAvatar();
                } else {
                    mensajeError = "Archivo no válido";
                    color = "color: red";
                }
            } else {
                if (fileInfo.getSize() > Constantes.TAMANIO_ARCHIVO) {
                    mensajeError = "El archivo pesa mas de el límite establecido.";
                } else {
                    mensajeError = "El archivo no pudo ser procesado. Contacte al administador del sistema.";
                }
                color = "color: red";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void modificarAvatar() {
        if (cuenta.actualizarAvatarUsuario(usuario)) {
            mensajeError = "Su avatar se modificó con éxito.";
            ParametroCache.inicializarUsuarios();
            ManejadorSesiones.modificarAvatar(ParametroCache.obtenerUsuarioTOPorId(usuario.getId()).getAvatar());
            file = null;
        } else {
            mensajeError = "Su avatar no se pudo modificar.";
        }
        color = mensajeError.contains("éxito") ? "color: green" : "color: red";
    }

    public void abrirPopUp() {
        mensajeError = "";
        color = "color: green";
        inicializar();
        popUp = true;
    }

    public void cerrarPopUp() {
        System.out.println("Cerrar");
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

    public int getTAMANIO_ARCHIVO() {
        return Constantes.TAMANIO_ARCHIVO;
    }
}
