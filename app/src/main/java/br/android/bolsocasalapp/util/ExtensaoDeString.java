package br.android.bolsocasalapp.util;

public class ExtensaoDeString {

    public static boolean validarCampo(String campo)
    {
        return  (campo != null) && (!campo.isEmpty()) ? true : false;
    }
}
