package com.pan.sware.catalogos;

import com.pan.sware.TO.CoordinacionTO;
import com.pan.sware.Util.Constantes;
import com.pan.sware.Util.Util;
import java.io.File;
import java.util.StringTokenizer;
import org.icefaces.ace.component.fileentry.*;
import org.icefaces.ace.component.fileentry.FileEntryResults.FileInfo;

/**
 *
 * @author estebanfcv
 */
public class CoordinacionPopUpAvatar {

    private CoordinacionTO coordinacion;
    private boolean popUp;
    private File file;
    private String extension;
    private String mensajeError;
    private String color;

    public CoordinacionPopUpAvatar(CoordinacionTO coordinacion) {
        this.coordinacion = coordinacion;
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
                    coordinacion.setAvatar(Util.convertirFileABytes(file));
                    mensajeError = "La imagen se seleccionó correctamente";
                    color = "color: green";
                } else {
                    mensajeError = "El archivo que seleccionó no es válido";
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

  

    public void abrirPopUp() {
        mensajeError = "";
        color = "color: green";
        inicializar();
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
        return Constantes.TAMANIO_ARCHIVO;
    }
}
