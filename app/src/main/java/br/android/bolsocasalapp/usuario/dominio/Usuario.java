package br.android.bolsocasalapp.usuario.dominio;

import com.google.firebase.database.Exclude;

import java.security.Principal;
import java.util.List;

public class Usuario {
    private String id;
    private String nomeCompleto;
    private String email;
    private String senha;
    private String emailDoConjuge;
    private boolean principal;
    private String token;


    public String getEmailDoConjuge() {
        return emailDoConjuge;
    }

    public void setEmailDoConjuge(String emailDoConjuge) {
        this.emailDoConjuge = emailDoConjuge;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario() {
    }

    public Usuario(String id, String nomeCompleto, String email, String senha, String emailDoConjuge, boolean principal, String token) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.emailDoConjuge = emailDoConjuge;
        this.principal = principal;
        this.token = token;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getConjuge() {
        return emailDoConjuge;
    }

    public void setConjuge(String conjuge) {
        this.emailDoConjuge = conjuge;
    }
}
