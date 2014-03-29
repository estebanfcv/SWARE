/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pan.sware.TO;

import java.awt.Image;
import java.io.File;

/**
 *
 * @author estebanfcv
 */
public class ImagenTO {
    Image imagen;
    String nombre;
    int idImagen;
    File fileImagen;

    public Image getImagen() {
        return imagen;
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public File getFileImagen() {
        return fileImagen;
    }

    public void setFileImagen(File fileImagen) {
        this.fileImagen = fileImagen;
    }
    
}
