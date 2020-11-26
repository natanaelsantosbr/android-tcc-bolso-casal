package br.android.bolsocasalapp.helper;

public class ExtensaoDeString {

    public static boolean validarCampo(String campo)
    {
        return  (campo != null) && (!campo.isEmpty()) ? true : false;
    }
}
