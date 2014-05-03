package com.pan.sware.Queries;

/**
 *
 * @author estebanfcv
 */
public class CatUsuarios {

    public static final String CONSULTAR_USUARIOS = "SELECT "
            + "ID,"
            + "USERNAME,"
            + "PASSWORD,"
            + "EMAIL,"
            + "AVATAR,"
            + "NOMBRE,"
            + "APELLIDO_PATERNO,"
            + "APELLIDO_MATERNO,"
            + "ID_PERFIL,"
            + "TELEFONO_CASA,"
            + "TELEFONO_TRABAJO,"
            + "TELEFONO_CELULAR,"
            + "BLOQUEADO "
            + "FROM USUARIOS WHERE BORRADO_LOGICO=0 AND ID_COORDINACION=?";

    public static final String INSERTAR_USUARIO = "INSERT INTO USUARIOS( "
            + "USERNAME,"
            + "PASSWORD,"
            + "EMAIL,"
            + "NOMBRE,"
            + "APELLIDO_PATERNO,"
            + "APELLIDO_MATERNO,"
            + "ID_PERFIL,"
            + "TELEFONO_CASA,"
            + "TELEFONO_TRABAJO,"
            + "TELEFONO_CELULAR,"
            + "BLOQUEADO,"
            + "FECHA_ALTA,"
            + "ID_COORDINACION,"
            + "PASSWORD_RANDOM) "
            + "VALUES("
            + "?,?,?,?,?,?,?,?,?,?,"
            + "?,?,?,?)";

    public static final String MODIFICAR_USUARIO = "UPDATE USUARIOS SET "
            + "EMAIL=?,"
            + "NOMBRE=?,"
            + "APELLIDO_PATERNO=?,"
            + "APELLIDO_MATERNO=?,"
            + "ID_PERFIL=?,"
            + "TELEFONO_CASA=?,"
            + "TELEFONO_TRABAJO=?,"
            + "TELEFONO_CELULAR=?,"
            + "BLOQUEADO=? "
            + "WHERE ID=?";

    public static final String ELIMINAR_USUARIO = "UPDATE USUARIOS SET BORRADO_LOGICO=1 WHERE ID=?";

    public static final String MODIFICAR_PASSWORD = "UPDATE USUARIOS SET PASSWORD=?, PASSWORD_RANDOM=0 WHERE ID=?";
}
