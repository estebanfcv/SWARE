/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pan.sware.Queries;

/**
 *
 * @author estebanfcv
 */
public class Usuarios {
    
    public static final String CONSULTAR_USUARIOS="SELECT ID,USERNAME,PASSWORD FROM USUARIOS";
    
    public static final String INSERTAR_IMAGENES= "INSERT INTO IMAGENES (IMAGEN,NOMBRE,EXTENSION) VALUES(?,?,?)";
    
    public static final String CONSULTAR_IMAGENES="SELECT ID,IMAGEN,NOMBRE,EXTENSION FROM IMAGENES LIMIT 1";
    
}
