package com.pan.sware.Queries;

/**
 *
 * @author estebanfcv
 */
public class Directorio {

    public static final String CONSULTAR_DIRECTORIO = "SELECT "
            + "ID,"
            + "NOMBRE,"
            + "TELEFONO1,"
            + "TELEFONO2,"
            + "TELEFONO3,"
            + "EMAIL,"
            + "COMENTARIO,"
            + "ID_COORDINACION "
            + "FROM DIRECTORIO WHERE BORRADO_LOGICO=0 AND ID_COORDINACION=?";

    public static final String INSERTAR_DIRECTORIO = "INSERT INTO DIRECTORIO ("
            + "NOMBRE,"
            + "TELEFONO1,"
            + "TELEFONO2,"
            + "TELEFONO3,"
            + "EMAIL,"
            + "COMENTARIO,"
            + "ID_COORDINACION) VALUES ("
            + "?,?,?,?,?,?,?) ";
    
    public static final String MODIFICAR_DIRECTORIO = "UPDATE DIRECTORIO SET "
            + "NOMBRE=?,"
            + "TELEFONO1=?,"
            + "TELEFONO2=?,"
            + "TELEFONO3=?,"
            + "EMAIL=?,"
            + "COMENTARIO=? "
            + "WHERE ID=?";
    
    public static final String ELIMINAR_DIRECTORIO = "UPDATE DIRECTORIO SET BORRADO_LOGICO=1 WHERE ID=?";
    
    

}
