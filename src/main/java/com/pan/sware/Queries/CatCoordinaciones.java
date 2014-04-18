package com.pan.sware.Queries;

/**
 *
 * @author estebanfcv
 */
public class CatCoordinaciones {
    
    public static final String CONSULTAR_COORDINACIONES="SELECT "
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
            + "AVATAR "
            + "FROM COORDINACIONES WHERE BORRADO_LOGICO=0";
    
    public static final String CONSULTAR_MUNICIPIOS_COORDINACION="SELECT "
            + "ID_COORDINACION,"
            + "ID_ESTADO,"
            + "ID_MUNICIPIO"
            + " FROM COORDINACION_MUNICIPIOS WHERE ID_COORDINACION =?";
    
}
