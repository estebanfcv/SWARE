package com.pan.sware.main;

import com.pan.sware.TO.AgendaTO;
import javax.faces.event.ActionEvent;

/**
 *
 * @author estebanfcv
 */
public class PopUpEvento {
    
    private AgendaTO agenda;
    private boolean popUp;

    public PopUpEvento() {
        inicializar();
    }
    
    private void inicializar(){
        popUp=false;
    }
    
    public void abrirPopUp(ActionEvent event){
        agenda = (AgendaTO) event.getComponent().getAttributes().get("ag");
        popUp=true;
    }
    
    public void cerrarPopUp(){
        popUp=false;
    }

    public AgendaTO getAgenda() {
        return agenda;
    }

    public boolean isPopUp() {
        return popUp;
    }
    
    
    
    
    
}
