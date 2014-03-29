package com.pan.sware.sesiones;

import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.listener.ContextListener;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author Cesar Villegas
 */
@SuppressWarnings({"CallToThreadDumpStack", "SleepWhileInLoop"})
public class HiloSesiones implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(HiloSesiones.class);
    private Thread hiloSesiones = null;
    private boolean corriendo = false;

    public void start() {
        hiloSesiones = new Thread(this);
        corriendo = true;
        hiloSesiones.start();
    }

    public void detener() {
        corriendo = false;
    }

    @Override
    public void run() {
        try {
            while (corriendo) {
                Date date = new Date();
                LOGGER.info("CORRIENDO HILO SESIONES::::" + date);
                validarSesiones(date);
                LOGGER.info("ACTUALIZANDO PARAMETRO CACHE::::::");
                ContextListener.inicializarParametroCache();
                Thread.sleep(60000);
            }
            System.out.println("Se detiene el hilo de sesiones " + new Date());
        } catch (Exception e) {
            corriendo = false;
            e.printStackTrace();
        }
    }

    private void validarSesiones(Date date) {
        Map<String, UsuarioTO> usuarios = ManejadorSesiones.getUsuariosRegistrados();
        Iterator<UsuarioTO> iter = usuarios.values().iterator();
        while (iter.hasNext()) {
            UsuarioTO usuario = iter.next();
            if (date.after(usuario.getFechaFinSesion())) {
                LOGGER.info("ELIMINANDO SESION DE.... " + usuario.getUsername());
                ManejadorSesiones.eliminarSesion(usuario);
            }
        }
    }
}
