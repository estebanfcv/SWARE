package com.pan.sware.catalogos;

import com.pan.sware.TO.CoordinacionTO;
import com.pan.sware.Util.Constantes;
import com.pan.sware.Util.Util;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.StringTokenizer;
import javax.faces.context.FacesContext;
import org.icefaces.ace.component.fileentry.*;

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
                    convertirFileABytes();
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

    private void convertirFileABytes() {
        try {
            try (FileInputStream fi = new FileInputStream(file)) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                for (int readNum; (readNum = fi.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum);
                }
                coordinacion.setAvatar(bos.toByteArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

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
}
