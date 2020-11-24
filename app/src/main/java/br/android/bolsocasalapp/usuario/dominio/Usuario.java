package br.android.bolsocasalapp.usuario.dominio;

import com.google.firebase.database.Exclude;

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

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Conjuge getConjuge() {
        return conjuge;
    }

    public void setConjuge(Conjuge conjuge) {
        this.conjuge = conjuge;
    }
}
