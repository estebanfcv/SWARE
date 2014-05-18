package com.pan.sware.directorio;

import com.pan.sware.TO.DirectorioTO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author estebanfcv
 */
@ManagedBean(name = "directorio")
@ViewScoped
public class DirectorioBean implements Serializable {

    private DirUsuarios dirUsuarios;
    private DirOtros dirOtros;
    private PopUpEmail popUpEmail;

    public DirectorioBean() {
        inicializar();
    }

    private void inicializar() {
//        popUpEmail = new PopUpEmail();
        dirUsuarios = new DirUsuarios();
        dirOtros = new DirOtros();

    }

    public void abrirPopUp(String lista) {
        System.out.println("1");
        popUpEmail = new PopUpEmail();
        System.out.println("2");
        popUpEmail.setListaCorreos(lista);
        System.out.println("3");
        popUpEmail.abrirPopUp();
        System.out.println("4");
        System.out.println("Visible es:::: "+popUpEmail.isVisible());
    }

    public DirUsuarios getDirUsuarios() {
        return dirUsuarios;
    }

    public DirOtros getDirOtros() {
        return dirOtros;
    }

    public PopUpEmail getPopUpEmail() {
        return popUpEmail;
    }
    
    

}
