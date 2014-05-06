package com.pan.sware.directorio;

/**
 *
 * @author estebanfcv
 */
public class PopUpEmail {
    
    private String listaCorreos;

    public PopUpEmail() {
        
    }
    
    public void abrirPopUp(){
        System.out.println("Abri el popUp");
        System.out.println("La lista de correos es:::: "+listaCorreos);
    }

    public String getListaCorreos() {
        return listaCorreos;
    }

    public void setListaCorreos(String listaCorreos) {
        this.listaCorreos = listaCorreos;
    }

    
    
}
