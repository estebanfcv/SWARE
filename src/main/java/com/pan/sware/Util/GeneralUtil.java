package com.pan.sware.Util;

import java.util.ArrayList;
import java.util.List;

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
}
