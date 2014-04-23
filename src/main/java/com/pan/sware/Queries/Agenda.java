package com.pan.sware.Queries;

/**
 *
 * @author estebanfcv
 */
public class Agenda {

    public static final String CONSULTAR_AGENDA = "SELECT "
            + "ID,"
            + "TITULO,"
            + "MENSAJE,"
            + "FECHA,"
            + "ID_USUARIO "
            + "FROM AGENDA WHERE BORRADO_LOGICO=0 AND FECHA BETWEEN ? AND ? AND ID_COORDINACION=? ORDER BY FECHA ASC";

    public static final String INSERTAR_AGENDA = "INSERT INTO AGENDA ("
            + "TITULO,"
            + "MENSAJE,"
            + "FECHA,"
            + "FECHA_ALTA,"
            + "ID_USUARIO,"
            + "ID_COORDINACION) "
            + "VALUES("
            + "?,?,?,?,?,?)";

    public static final String MODIFICAR_AGENDA = "UPDATE AGENDA SET "
            + "TITULO=?,"
            + "MENSAJE=?,"
            + "FECHA=? "
            + "WHERE ID=?";
    
    public static final String ELIMINAR_AGENDA =" UPDATE AGENDA SET BORRADO_LOGICO=1 WHERE ID=?";
}
