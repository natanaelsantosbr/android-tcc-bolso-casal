package br.android.bolsocasalapp.usuario.dominio;

public class Usuario {
    private String nomeCompleto;
    private String email;
    private String senha;
    private Conjuge conjuge;

    public Usuario(String nomeCompleto, String email, String senha, Conjuge conjuge) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.conjuge = conjuge;
    }
}
