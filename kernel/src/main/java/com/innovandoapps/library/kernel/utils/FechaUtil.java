package com.innovandoapps.library.kernel.utils;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Clase con funciones utilitarias de fecha
 * @author Marcos Ramirez
 */
public class FechaUtil {

    /**
     * Retorna una cadena de fecha actual con formato yyyy-MM-dd HH:mm:ss.SSS
     * @return String de fecha
     */
    public static String getFechaActual(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * Retorna una cadena de fecha actual con formato yyyy-MM-dd
     * @return String de fecha
     */
    public static String getFechaDia(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * Retorna una cadena de fecha actual con formato yyyy-MM-dd:HH:mm:ss
     * @return String de fecha
     */
    public static String getFechaCadena(){
        return new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss").format(new Date());
    }

    public static String getFechaCadena2(){
        return new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
    }

    /**
     * Retorna cadena fecha con formato yyyy-MM-dd HH:mm:ss.SSS a partir de milisegundos
     * @param time tiempo en milisegundos a convertir a fecha
     * @return String de fecha
     */
    public static String milisegunToDate(long time){
        Date date=new Date(time);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
    }

    /**
     * Retorna cadena de Hora en formato HH:mm:ss a partir de milisegundos
     * @param time tiempo en milisegundos a convertir en hora
     * @return String de hora
     */
    public static String SegundoToHora(long time){
        long milisegundos = time*1000;
        long diffsegundos = milisegundos / 1000 % 60;
        long diffminutos = milisegundos / (60 * 1000) % 60;
        long diffHoras = milisegundos / (60 * 60 * 1000) % 24;

        DecimalFormat df = new DecimalFormat("00");
        return String.format("%s:%s:%s",df.format(diffHoras),df.format(diffminutos),df.format(diffsegundos));
    }

    public static String getHoraActual(){
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public static Date stringToDate(String sDate1){
        try {
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
            return date1;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date stringToDateWithoutTime(String sDate1){
        try {
            Date date1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sDate1);
            return date1;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String DateToString(Date fecha){
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String s = formatter.format(fecha);
        return s;
    }

    public static long DateToMiliseconds(String fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(fecha);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getFechaActualMilisegundos(){
        Date date = new Date();
        return date.getTime();
    }
}
