package com.pan.sware.Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    
    public static boolean validarEmail(String email){
        Pattern patternEmail;
        patternEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = patternEmail.matcher(email);
        return matcher.matches();
    }
    
    public static String generarPasswordAleatorio() {
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 8; i++) {
            if (rnd.nextBoolean()) {
                // Numeros
                int caracterAscii = rnd.nextInt(10) + 48;
                sb.append((char) caracterAscii);
            } else {
                // Letras
                int caracterAscii = rnd.nextInt(26) + 65;
                sb.append((char) caracterAscii);
            }
        }
        return sb.toString();
    }
    
    public static String encryptMD5(String fuente) throws NoSuchAlgorithmException, CloneNotSupportedException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        ByteBuffer input = ByteBuffer.wrap(fuente.getBytes());
        md.update(input);
        MessageDigest tc1 = (MessageDigest) md.clone();
        byte[] convertido = tc1.digest();
        BigInteger b = new BigInteger(1, convertido);
        return String.format("%1$032X", b);
    }
    
    public static Date obtenerSoloFecha(Date fecha){
        Calendar calendar  = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    public static Date cambiarFecha(Date fecha){
        Calendar calendar  = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
        
    }
    
    public static byte[] convertirFileABytes(File file) {
        byte[] bytes= new byte[0];
        try {
            try (FileInputStream fi = new FileInputStream(file); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                byte[] buf = new byte[1024];
                for (int readNum; (readNum = fi.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum);
                }
                bytes=bos.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return bytes;
    }
}
