package com.pan.sware.cuenta;

import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.Util.Constantes;
import com.pan.sware.Util.ParametroCache;
import com.pan.sware.Util.Util;
import com.pan.sware.sesiones.ManejadorSesiones;
import java.io.File;
import java.io.FileInputStream;
import java.util.StringTokenizer;
import javax.faces.context.FacesContext;
import org.icefaces.ace.component.fileentry.*;

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
    private boolean bandera;

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
        bandera = file == null;

    }

    public void sampleListener(FileEntryEvent e) {
        try {
            file = null;
            FileEntry fe = (FileEntry) e.getComponent();
            FileEntryResults results = fe.getResults();
            extension = ".";
            System.out.println("EL contentType() es:::::::: " + results.getFiles().get(0).getContentType());
            StringTokenizer token = new StringTokenizer(results.getFiles().get(0).getContentType(), "/");
            while (token.hasMoreTokens()) {
                token.nextToken();
                extension += token.nextToken();
            }
            System.out.println("La extension del archivo es::::: " + extension);
            if (results.getFiles().get(0).isSaved()) {
                if (Util.archivosPermitidos(extension)) {
                    file = results.getFiles().get(0).getFile();
                    convertirFileABytes();
                    mensajeError = "Archivo Correcto";
                    color = "color: green";
                    System.out.println("File ::::: " + file.getPath());
                } else {
                    mensajeError = "Archivo no valido";
                    color = "color: red";
                }
            } else {
                if (results.getFiles().get(0).getStatus().getFacesMessage(
                        FacesContext.getCurrentInstance(), fe, results.getFiles().get(0)).getSummary().
                        contains("exceeds the maximum file size")) {
                    mensajeError = "El Archivo es muy pesado.";
                    color = "color: red";
                } else {
                    mensajeError = "El archivo no pudo ser procesado. Contacte al administador del sistema.";
                    color = "color: red";
                }
            }
            bandera = file == null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void modificarAvatar() {
        if (file != null) {
            if (cuenta.actualizarAvatarUsuario(usuario)) {
                mensajeError = "La imagen se modificó con éxito.";
                 color = "color: green";
                ParametroCache.inicializarUsuarios();
                ManejadorSesiones.modificarAvatar(ParametroCache.obtenerUsuarioTOPorId(usuario.getId()).getAvatar());
                file = null;
                bandera = file == null;
            } else {
                mensajeError = "Su avatar no se pudo modificar.";
                color = "color: red";
            }
        }
    }

    private void convertirFileABytes() {
        try {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                fileInputStream.read(usuario.getAvatar());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    public boolean isBandera() {
        return bandera;
    }
}
