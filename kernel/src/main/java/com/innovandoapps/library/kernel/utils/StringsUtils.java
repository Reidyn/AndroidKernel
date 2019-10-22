package com.innovandoapps.library.kernel.utils;

import java.text.DecimalFormat;
import java.util.Random;

public class StringsUtils {

    public static String DecimalString(long value){
        if(value > 0){
            return String.format("%,d",value);
        }else{
            return "0";
        }
    }

    public static String DecimalUSFormat(Double value){
        DecimalFormat formateador = new DecimalFormat("###.###,##");
        return formateador.format (value);
    }

    public static String DecimalUEFormat(Double value){
        DecimalFormat formateador = new DecimalFormat("###,###.##");
        return formateador.format (value);
    }

    public static String DecimalDollarFormat(Double value){
        DecimalFormat formateador = new DecimalFormat("###,###.00");
        return formateador.format (value);
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(100);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
