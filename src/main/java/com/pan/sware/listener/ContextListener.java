package com.pan.sware.listener;

import com.pan.sware.Util.ParametroCache;
import com.pan.sware.db.DBConnectionManager;
import com.pan.sware.sesiones.HiloSesiones;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author estebanfcv
 */
public class ContextListener implements ServletContextListener {

    private static final HiloSesiones hiloSesiones = new HiloSesiones();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        
        String jndiBD = servletContextEvent.getServletContext().getInitParameter("JNDI_BD");
        String debug = servletContextEvent.getServletContext().getInitParameter("debugBD");
        DBConnectionManager.initInstance( jndiBD, "true".equals(debug));
        ParametroCache.inicializarEstados();
        ParametroCache.inicializarMunicipios();
        inicializarParametroCache();
        //
        hiloSesiones.start();
        //        
        Logger.getLogger(ContextListener.class.getName()).log(Level.FINEST, "contextInitialized");
    }

    public static void inicializarParametroCache() {
        System.out.println("SE INICIALIZA TODO");
        ParametroCache.inicializarCoordinaciones();
        ParametroCache.inicializarUsuarios();
        ParametroCache.inicializarCampanias();
        ParametroCache.inicializarAgenda();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        hiloSesiones.detener();
    }
}