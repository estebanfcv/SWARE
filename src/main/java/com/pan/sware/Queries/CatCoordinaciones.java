package com.pan.sware.Queries;

/**
 *
 * @author estebanfcv
 */
public class CatCoordinaciones {

    public static final String CONSULTAR_COORDINACIONES = "SELECT "
            + "ID,"
            + "NOMBRE,"
            + "NOMBRE_RESPONSABLE,"
            + "APELLIDO_PATERNO,"
            + "APELLIDO_MATERNO,"
            + "CALLE,"
            + "NUMERO_EXTERIOR,"
            + "NUMERO_INTERIOR,"
            + "COLONIA,"
            + "CODIGO_POSTAL,"
            + "TELEFONO,"
            + "EMAIL,"
            + "FECHA_ALTA,"
            + "AVATAR,"
            + "USERNAME,"
            + "PASSWORD "
            + "FROM COORDINACIONES WHERE BORRADO_LOGICO=0";

    public static final String INSERTAR_COORDINACION = "INSERT INTO COORDINACIONES ("
            + "NOMBRE, "
            + "NOMBRE_RESPONSABLE,"
            + "APELLIDO_PATERNO,"
            + "APELLIDO_MATERNO,"
            + "CALLE,"
            + "NUMERO_EXTERIOR,"
            + "NUMERO_INTERIOR,"
            + "COLONIA,"
            + "CODIGO_POSTAL,"
            + "TELEFONO,"
            + "EMAIL,"
            + "FECHA_ALTA,"
            + "AVATAR,"
            + "USERNAME,"
            + "PASSWORD) "
            + "VALUES("
            + "?,?,?,?,?,?,?,?,?,?,"
            + "?,?,?,?,?)";

    public static final String MODIFICAR_COORDINACION = "UPDATE COORDINACIONES SET "
            + "NOMBRE=?,"
            + "NOMBRE_RESPONSABLE=?,"
            + "APELLIDO_PATERNO=?,"
            + "APELLIDO_MATERNO=?,"
            + "CALLE=?,"
            + "NUMERO_EXTERIOR=?,"
            + "NUMERO_INTERIOR=?,"
            + "COLONIA=?,"
            + "CODIGO_POSTAL=?,"
            + "TELEFONO=?,"
            + "AVATAR=? "
            + "WHERE ID=?";
    
    public static final String ELIMINAR_COORDINACION = "UPDATE COORDINACIONES SET BORRADO_LOGICO=1 WHERE ID=?";



}
