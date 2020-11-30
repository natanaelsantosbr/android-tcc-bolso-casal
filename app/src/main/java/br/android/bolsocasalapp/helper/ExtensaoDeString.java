package br.android.bolsocasalapp.helper;

import java.text.NumberFormat;
import java.text.ParseException;

public class ExtensaoDeString {

    public static boolean validarCampo(String campo)
    {
        return  (campo != null) && (!campo.isEmpty()) ? true : false;
    }

    public static double ConverterRealParaString(String texto) {
        try {
            return NumberFormat.getCurrencyInstance().parse(texto).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
            String cleanString = texto.replaceAll("\\D", "");
            try {
                double money = Double.parseDouble(cleanString);
                return money / 100;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }
}
