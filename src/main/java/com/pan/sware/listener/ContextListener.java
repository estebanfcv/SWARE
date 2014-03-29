package com.pan.sware.listener;

import com.pan.sware.db.DBConnectionManager;
import com.pan.sware.sesiones.HiloSesiones;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Cesar Villegas
 */
public class ContextListener implements ServletContextListener {

    private static final HiloSesiones hiloSesiones = new HiloSesiones();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        
        String jndiBD = servletContextEvent.getServletContext().getInitParameter("JNDI_BD");
        System.out.println("LECTURA::::: "+jndiBD);
        String debug = servletContextEvent.getServletContext().getInitParameter("debugBD");
        DBConnectionManager.initInstance( jndiBD, "true".equals(debug));
        inicializarParametroCache();
        //
        hiloSesiones.start();
        //        
        Logger.getLogger(ContextListener.class.getName()).log(Level.FINEST, "contextInitialized");
    }

    public static void inicializarParametroCache() {
        /*
         * Es importante inicializar todo en este orden, o si no, el Cache no
         *  se inicializará correctamente y ciertos campos tendrán null
         */
        
        System.out.println("SE INICIALIZA TODO");
//        ParametroCache.inicializarParametros();
//        ParametroCache.inicializarCorporativos();
//        ParametroCache.inicializarCiudades();
//        ParametroCache.inicializarEmpresas();
//        ParametroCache.inicializarPerfiles();
//        ParametroCache.inicializarUsuarios();
//        ParametroCache.inicializarSucursales();
//        ParametroCache.inicializarColonias();
//        ParametroCache.inicializarCalles();
//        ParametroCache.inicializarBancos();
//        ParametroCache.inicializarCuentas();
//        ParametroCache.inicializarNodos();
//        ParametroCache.inicializarServicios();
//        ParametroCache.inicializarSenales();
//        ParametroCache.inicializarConceptosCobro();
//        ParametroCache.inicializarVendedores();
//        ParametroCache.inicializarVehiculos();
//        ParametroCache.inicializarTecnicos();
//        ParametroCache.inicializarCombos();
//        ParametroCache.inicializarMateriales();
//        ParametroCache.inicializarLimiteMateriales();
//        ParametroCache.inicializarEquipos();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        hiloSesiones.detener();
    }
}
