package com.pan.sware.cuenta;

import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.Util.GeneralUtil;
import java.io.File;
import java.io.FileInputStream;
import java.util.StringTokenizer;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.icefaces.ace.component.fileentry.*;
import org.icefaces.application.PushRenderer;

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
    private static final String PUSH_GROUP = "colorPage";

    public PopUpCambiarAvatar(UsuarioTO usuario) {
        PushRenderer.addCurrentSession(PUSH_GROUP);
        this.usuario = usuario;
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
//            FacesContext ctx = FacesContext.getCurrentInstance();
//            FacesMessage msg = new FacesMessage();
//            msg.setSeverity(null);
//            msg.setSummary("mysummary");
//            msg.setDetail("mydetail");
//            ctx.addMessage(fe.getClientId(), msg);
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
                if (GeneralUtil.archivosPermitidos(extension)) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void convertirFileABytes() {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(usuario.getAvatar());
            fileInputStream.close();
            PushRenderer.render(PUSH_GROUP);
            System.out.println(GeneralUtil.debugImprimirContenidoObjecto(usuario));
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
}
