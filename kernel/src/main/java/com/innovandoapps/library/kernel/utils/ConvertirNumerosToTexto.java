package com.innovandoapps.library.kernel.utils;

import android.util.Log;

public class ConvertirNumerosToTexto {
	
	private static final String[] UNIDADES = { "", "UN ", "DOS ", "TRES ","CUATRO ", "CINCO ", "SEIS ", 
											   "SIETE ", "OCHO ", "NUEVE ", "DIEZ ","ONCE ", "DOCE ", "TRECE ", "CATORCE ", "QUINCE ", 
											   "DIECISEIS","DIECISIETE", "DIECIOCHO", "DIECINUEVE", "VEINTE" };
	
	private static final String[] DECENAS = { "VENTI", "TREINTA ", "CUARENTA ","CINCUENTA ", "SESENTA ", 
											 "SETENTA ", "OCHENTA ", "NOVENTA ","CIEN " };

	private static final String[] CENTENAS = { "CIENTO ", "DOSCIENTOS ","TRESCIENTOS ", "CUATROCIENTOS ", 
											   "QUINIENTOS ", "SEISCIENTOS ","SETECIENTOS ", "OCHOCIENTOS ", "NOVECIENTOS " };
	
	public static String convertir(String number){
		
		String cadena=number.replace(".","#");
		String parts[]=cadena.split("#");
		StringBuilder converted = new StringBuilder();
		int dif=0;
		int millon=0;
		int miles=0;
		int cientos=0;
		
		for(int i=0;i<parts.length;i++){
			dif=parts.length-(i+1);
			switch (dif) {
				case 2:
					millon=Integer.parseInt(parts[i]);
					if (millon == 1)
			            converted.append("UN MILLON ");
			        else if (millon > 1)
			        	converted.append(convertNumber(String.valueOf(millon))+ "MILLONES ");
				break;
				case 1:
					miles =Integer.parseInt(parts[i]);
					if (miles == 1)
				        converted.append("MIL ");
				    else if (miles > 1)
				        converted.append(convertNumber(String.valueOf(miles)) + "MIL ");
				break;
				case 0:
					try{
						cientos=Integer.parseInt(parts[i]);
						if (cientos == 1)
							converted.append("UN");
					}catch (NumberFormatException ex){
						converted.append("");
					}
				break;
			}
		}
		
		 if (millon + miles + cientos == 0)
	            converted.append("CERO");
	     if (cientos > 1)
	            converted.append(convertNumber(String.valueOf(cientos)));
	     Log.i("Recibido",converted.toString());
		return converted.toString();
	}
	
	private static String convertNumber(String number) {

        if (number.length() > 3)
            throw new NumberFormatException(
                    "La longitud maxima debe ser 3 digitos");

        // Caso especial con el 100
        if (number.equals("100")) {
            return "CIEN";
        }

        StringBuilder output = new StringBuilder();
        if (getDigitAt(number, 2) != 0)
            output.append(CENTENAS[getDigitAt(number, 2) - 1]);

        int k = Integer.parseInt(String.valueOf(getDigitAt(number, 1))
                + String.valueOf(getDigitAt(number, 0)));

        if (k <= 20)
            output.append(UNIDADES[k]);
        else if (k > 30 && getDigitAt(number, 0) != 0)
            output.append(DECENAS[getDigitAt(number, 1) - 2] + "Y "
                    + UNIDADES[getDigitAt(number, 0)]);
        else
            output.append(DECENAS[getDigitAt(number, 1) - 2]
                    + UNIDADES[getDigitAt(number, 0)]);

        return output.toString();
    }
	
	private static int getDigitAt(String origin, int position) {
        if (origin.length() > position && position >= 0)
            return origin.charAt(origin.length() - position - 1) - 48;
        return 0;
    }

    public static String cadenaDecimal(Double valor){
		String doubleAsString = String.valueOf(valor);
		int indexOfDecimal = doubleAsString.indexOf(".");
		int decimal = Integer.parseInt(doubleAsString.substring(indexOfDecimal + 1));

		String dc = Integer.toString(decimal);
		if(decimal<10){
			dc = "0" + dc;
		}

		return "con " + dc + "/100";
	}
}
