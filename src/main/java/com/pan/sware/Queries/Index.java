package com.pan.sware.Queries;

/**
 *
 * @author estebanfcv
 */
public class Index {

    public static final String CONSULTAR_USUARIOS = "SELECT "
            + "ID,"
            + "USERNAME,"
            + "PASSWORD,"
            + "EMAIL,"
            + "AVATAR,"
            + "NOMBRE,"
            + "APELLIDO_PATERNO,"
            + "APELLIDO_MATERNO,"
            + "FECHA_ALTA,"
            + "ID_PERFIL,"
            + "ID_COORDINACION,"
            + "TELEFONO_CASA,"
            + "TELEFONO_OFICINA,"
            + "TELEFONO_CELULAR"
            + " FROM USUARIOS";

    public static final String CONSULTAR_ESTADOS = "SELECT "
            + "ID,"
            + "NOMBRE "
            + "FROM ESTADOS";
    
    public static final String CONSULTAR_MUNICIPIOS ="SELECT "
            + "ID,"
            + "NOMBRE,"
            + "ID_ESTADO "
            + "FROM MUNICIPIOS";

}
