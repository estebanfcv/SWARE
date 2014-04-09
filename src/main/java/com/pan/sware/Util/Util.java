package com.pan.sware.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author estebanfcv
 */
public class Util {
    
    public static boolean archivosPermitidos(String extension){
        List<String> extensionesPermitidas = new ArrayList<>();
        extensionesPermitidas.add(".png");
        extensionesPermitidas.add(".jpg");
        extensionesPermitidas.add(".jpeg");
        extensionesPermitidas.add(".gif");
        for(String s: extensionesPermitidas){
            if (s.equals(extension)){
                return true;
            }
        }
        return false;
    }
    
    public static boolean isUpdatePhase(ValueChangeEvent event) {
        PhaseId phaseId = event.getPhaseId();
        boolean ret = false;
        if (phaseId.equals(PhaseId.ANY_PHASE)) {
            event.setPhaseId(PhaseId.UPDATE_MODEL_VALUES);
            event.queue();
            ret = false;
        } else if (phaseId.equals(PhaseId.UPDATE_MODEL_VALUES)) {
            ret = true;
        }
        return ret;
    }
    
        public static String debugImprimirContenidoObjecto(Object o) {
        if (null == o) {
            return "null";
        }
        StringBuilder sb = new StringBuilder("Objeto clase: ").append(o.getClass().getName()).append(" - ").append(o.toString()).append('\n');

        try {

            for (java.lang.reflect.Field f : o.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                sb = sb.append(f.getName()).append(" - ").append(f.get(o)).append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String debugImprimirContenidoListaObjeto(Collection<? extends Object> lista) {
        if (null == lista) {
            return "null";
        }
        StringBuilder sb = new StringBuilder("Collection clase: ").append(lista.getClass().getName()).append(" - ").append(lista.toString()).append('\n');

        try {

            for (Object o : lista) {

                sb = sb.append("Objeto clase: ").append(o.getClass().getName()).append(" - ").append(o.toString()).append('\n');

                for (java.lang.reflect.Field f : o.getClass().getDeclaredFields()) {
                    f.setAccessible(true);
                    sb = sb.append(f.getName()).append(" - ").append(f.get(o)).append('\n');
                }

                sb = sb.append("=============================\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
