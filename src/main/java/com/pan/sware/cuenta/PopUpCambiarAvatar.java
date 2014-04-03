package com.pan.sware.cuenta;


/**
 *
 * @author estebanfcv
 */
public class PopUpCambiarAvatar {

    private boolean popUp;

    public PopUpCambiarAvatar() {

        inicializar();
    }

    private void inicializar() {
        popUp = false;

    }
    
    public void abrirPopUp(){
        System.out.println("Se abre el popUp");
        popUp = true;
    }
    
    public void cerrarPopUp(){
         popUp = false;
    }

    public boolean isPopUp() {
        return popUp;
    }

}
