package com.pan.sware.directorio;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author estebanfcv
 */
@ManagedBean(name = "directorio")
@ViewScoped
public class DirectorioBean {
    
    private DirUsuarios dirUsuarios;

    public DirectorioBean() {
        inicializar();
    }
    
    private void inicializar(){
        dirUsuarios = new DirUsuarios();
    }

    public DirUsuarios getDirUsuarios() {
        return dirUsuarios;
    }
    
    
    
}
