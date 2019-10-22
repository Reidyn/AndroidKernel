package com.innovandoapps.library.kernel.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by windows 8.1 on 18/02/2018.
 */

public class ValidadorInput {

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isCelularValid(String celular){
        String CELL_PATTERN = "(09[6-9]{1})+([1-6]{1})+([0-9]{6})";
        Pattern pattern = Pattern.compile(CELL_PATTERN);
        Matcher matcher = pattern.matcher(celular);
        return matcher.matches();
    }

    public static boolean isOnlyText(String text) {
        String TEXT_PATTERN = "^[a-zA-ZäÄëËïÏöÖüÜáéíóúáéíóúÁÉÍÓÚÂÊÎÔÛâêîôûàèìòùÀÈÌÒÙ ]*$";
        Pattern pattern = Pattern.compile(TEXT_PATTERN);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    public static boolean isOnlyNumber(String text) {
        String NUMBER_PATTERN = "^[0-9]*$";
        Pattern pattern = Pattern.compile(NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}
