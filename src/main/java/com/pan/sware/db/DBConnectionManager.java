package com.pan.sware.db;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class DBConnectionManager {

    private final static Logger LOGGER = Logger.getLogger(DBConnectionManager.class);
    public final static String BD = "BD";
    private Map<String, ConnectionManager> managers = new HashMap<>();
    private static DBConnectionManager instance = null;
    public static int conexionesTotalesAbiertasLectura = 0;
    public static int conexionesTotalesAbiertasEscritura = 0;

    private DBConnectionManager( String jndiBD, boolean debug) {
        init( jndiBD, debug);
    }

    public static synchronized DBConnectionManager getInstance() {
        return instance;
    }

    public static void initInstance( String jndiLectura, boolean debug) {
        if (instance == null) {
            instance = new DBConnectionManager( jndiLectura, debug);
        }
    }

    private void init( String jndiBD, boolean debug) {

        ConnectionManager connectionManager = new ConnectionManager();
        System.out.println("EL JL :"+jndiBD);
        connectionManager.setJndiVariables(jndiBD);
        connectionManager.setDebug(debug);
        managers.put(BD, connectionManager);
    }

    public Connection getConnection(String baseDatos) {
        ConnectionManager connectionManager = managers.get(baseDatos);
        
        if (connectionManager == null) {
            throw new IllegalArgumentException("Base de Datos no existe o no inicializada");
        }
        switch (baseDatos) {
            case BD:
                conexionesTotalesAbiertasLectura++;
                LOGGER.debug("getConnection base de datos:Lectura conexionesTotalesAbiertas: " + conexionesTotalesAbiertasLectura);
                break;
          
        }
        return connectionManager.getConexion();
    }
}
