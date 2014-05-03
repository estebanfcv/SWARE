package com.pan.index;

import com.pan.sware.TO.PerfilTO;
import com.pan.sware.TO.UsuarioTO;

/**
 *
 * @author estebanfcv
 */
public class PermisosUsuario {

    public UsuarioTO establecerPermisosUsuarioMaestro(UsuarioTO u) {
        PerfilTO p = new PerfilTO();
        p.setNombre("Administrador Maestro");
        p.setTiempoSesion(86400000L);
        p.setMiCuenta(1);
        p.setCoordinaciones(1);
        p.setParametrosGlobales(1);
        p.setAgenda(1);
        p.setCampania(1);
        p.setPerfiles(1);
        p.setUsuarios(1);
        u.setPerfil(p);
        return u;
    }

    public UsuarioTO establecerPermisosCoordinacion(UsuarioTO u) {
        PerfilTO p = new PerfilTO();
        p.setNombre("Coordinacion");
        p.setTiempoSesion(86400000L);
        p.setMiCuenta(1);
        p.setCoordinaciones(1);
        p.setParametrosGlobales(1);
        p.setAgenda(1);
        p.setCampania(1);
        p.setPerfiles(1);
        p.setUsuarios(1);
        u.setPerfil(p);
        return u;
    }
}
