package com.pan.sware.Queries;

/**
 *
 * @author estebanfcv
 */
public class CatCampanias {

    public static final String CONSULTAR_CAMPANIAS = "SELECT "
            + "ID,"
            + "NOMBRE,"
            + "COMENTARIO,"
            + "FECHA,"
            + "FECHA_ALTA,"
            + "ID_COORDINACION,AVATAR "
            + "FROM CAMPANIAS WHERE BORRADO_LOGICO=0";

    public static final String CONSULTAR_MUNICIPIOS_CAMPANIA = "SELECT "
            + "ID_COORDINACION,"
            + "ID_ESTADO,"
            + "ID_MUNICIPIO,"
            + "ID_CAMPANIA"
            + " FROM CAMPANIA_MUNICIPIOS WHERE ID_CAMPANIA =?";

    public static final String INSERTAR_CAMPANIA = " INSERT INTO CAMPANIAS ("
            + "NOMBRE,"
            + "COMENTARIO,"
            + "FECHA,"
            + "FECHA_ALTA,"
            + "ID_COORDINACION,AVATAR) "
            + "VALUES("
            + "?,?,?,?,?,?)";

    public static final String INSERTAR_CAMPANIA_MUNICIPIOS = "INSERT INTO CAMPANIA_MUNICIPIOS ("
            + "ID_COORDINACION,"
            + "ID_CAMPANIA,"
            + "ID_ESTADO,"
            + "ID_MUNICIPIO) "
            + "VALUES("
            + "?,?,?,?)";

    public static final String MODIFICAR_CAMPANIA = "UPDATE CAMPANIAS SET "
            + "NOMBRE=?,"
            + "COMENTARIO=?,"
            + "FECHA=?,"
            + "AVATAR=? "
            + "WHERE ID=?";
    
    public static final String ELMINAR_CAMPANIA="UPDATE CAMPANIAS SET BORRADO_LOGICO=1 WHERE ID=?";

    public static final String ELIMINAR_MUNICIPIOS_CAMPANIA = "DELETE FROM CAMPANIA_MUNICIPIOS WHERE ID_CAMPANIA =?";
    
    

}
