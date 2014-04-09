/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pan.sware.inicio;

import com.pan.sware.DAO.LoginDAO;
import com.pan.sware.Util.Util;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.icefaces.ace.component.fileentry.*;

/**
 *
 * @author estebanfcv
 */
@ManagedBean(name = "login2")
@ViewScoped
public class Index2Bean implements Serializable {

    private File file;
    private String ruta = "";
    private String nombre;
    private boolean pop;
    private String extension;
    private byte[] bytes;
    private int tamanioArchivo;
    private String mensajeError;

    public Index2Bean() {
        inicializar();
    }

    private void inicializar() {
        mensajeError = "";
        tamanioArchivo = 921600;
        pop = true;
        file = null;
    }

    public void sampleListener(FileEntryEvent e) {
        file = null;
        FileEntry fe = (FileEntry) e.getComponent();
        FileEntryResults results = fe.getResults();
        extension = ".";
        StringTokenizer token = new StringTokenizer(results.getFiles().get(0).getContentType(), "/");
        while (token.hasMoreTokens()) {
            token.nextToken();
            extension += token.nextToken();
        }
        System.out.println("La extension del archivo es::::: " + extension);
        if (results.getFiles().get(0).isSaved()) {
            if (Util.archivosPermitidos(extension)) {
                file = results.getFiles().get(0).getFile();
                mensajeError= "Archivo Correcto";
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
    }

    public void consultar() {
//         f = new File(ruta)
        LoginDAO l = new LoginDAO();
        System.out.println("Resultado:" + l.guardarImagen(file.getPath(), nombre, extension));

    }

    public void cerrarpopUpManuales() {
        pop = false;
    }

    public void crear() {
        try {
            LoginDAO l = new LoginDAO();
            bytes = readIntoByteArray(l.consultarImagenes());

        } catch (IOException ex) {
            Logger.getLogger(Index2Bean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void limpiar(){
        inicializar();
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isPop() {
        return pop;
    }

    private byte[] readIntoByteArray(InputStream in) throws IOException {
        byte[] buffer = new byte[4096];
        int bytesRead;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        out.flush();

        return out.toByteArray();
    }

    public byte[] getBytes() {
        return bytes;
    }

    public int getTamanioArchivo() {
        return tamanioArchivo;
    }

    public String getMensajeError() {
        return mensajeError;
    }
}
