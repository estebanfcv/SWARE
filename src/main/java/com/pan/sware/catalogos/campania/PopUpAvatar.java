package com.pan.sware.catalogos.campania;

import com.pan.sware.TO.CampaniaTO;
import com.pan.sware.Util.Constantes;
import com.pan.sware.Util.Util;
import java.io.File;
import java.util.StringTokenizer;
import org.icefaces.ace.component.fileentry.*;
import javax.faces.context.FacesContext;

/**
 *
 * @author estebanfcv
 */
public class PopUpAvatar {

    private CampaniaTO campania;
    private boolean popUp;
    private File file;
    private String extension;
    private String mensajeError;
    private String color;

    public PopUpAvatar(CampaniaTO campania) {
        this.campania = campania;
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
            FileEntry fe = (FileEntry) e.getComponent();
            FileEntryResults results = fe.getResults();
            extension = ".";
            StringTokenizer token = new StringTokenizer(results.getFiles().get(0).getContentType(), "/");
            while (token.hasMoreTokens()) {
                token.nextToken();
                extension += token.nextToken();
            }
            if (results.getFiles().get(0).isSaved()) {
                if (Util.archivosPermitidos(extension)) {
                    file = results.getFiles().get(0).getFile();
                    campania.setAvatar(Util.convertirFileABytes(file));
                    mensajeError = "La imagen se seleccionó correctamente";
                    color = "color: green";
                    System.out.println("File ::::: " + file.getPath());
                } else {
                    mensajeError = "El archivo que seleccionó no es válido";
                    color = "color: red";
                }
            } else {
                if (results.getFiles().get(0).getStatus().getFacesMessage(
                        FacesContext.getCurrentInstance(), fe, results.getFiles().get(0)).getSummary().
                        contains("se ha rechazado el requerimiento porque su tamaño excede el rango permitido")) {
                    mensajeError = "La imagen es muy pesada.";
                    color = "color: red";
                } else {
                    mensajeError = "La imagen no pudo ser seleccionada. Contacte al administador del sistema.";
                    color = "color: red";
                }
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
