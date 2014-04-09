package com.pan.sware.cuenta;

import com.pan.sware.TO.UsuarioTO;
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
    private final int TAMANIO_ARCHIVO = 921600;
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
                    System.out.println("File ::::: " + file.getPath());
                } else {
                    mensajeError = "Archivo no valido";
                }
            } else {
                if (results.getFiles().get(0).getStatus().getFacesMessage(
                        FacesContext.getCurrentInstance(), fe, results.getFiles().get(0)).getSummary().
                        contains("exceeds the maximum file size")) {
                    mensajeError = "El Archivo es muy pesado";
                } else {
                    mensajeError = "El archivo no pudo ser procesado. Contacte al administador del sistema.";
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
                mensajeError = "Correcto";
                ParametroCache.inicializarUsuarios();
                ManejadorSesiones.modificarAvatar(ParametroCache.obtenerUsuarioTOPorId(usuario.getId()).getAvatar());
                file = null;
                bandera = file == null;
            } else {
                mensajeError = "Incorrecto";
            }
        } 
    }

    private void convertirFileABytes() {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(usuario.getAvatar());
            fileInputStream.close();
            System.out.println(Util.debugImprimirContenidoObjecto(usuario));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void abrirPopUp() {
        System.out.println("Se abre el popUp");
        popUp = true;
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

    public int getTAMANIO_ARCHIVO() {
        return TAMANIO_ARCHIVO;
    }

    public boolean isBandera() {
        return bandera;
    }

}
