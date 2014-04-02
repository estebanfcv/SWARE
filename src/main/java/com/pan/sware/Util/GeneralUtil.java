package com.pan.sware.Util;

import java.util.ArrayList;
import java.util.List;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author estebanfcv
 */
public class GeneralUtil {
    
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
}
