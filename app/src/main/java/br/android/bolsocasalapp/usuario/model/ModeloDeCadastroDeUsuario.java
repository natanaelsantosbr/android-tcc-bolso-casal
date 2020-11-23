package br.android.bolsocasalapp.usuario.model;

public class ModeloDeCadastroDeUsuario {
    private String nomeCompleto;
    private String email;
    private String senha;
    private String emailDoParticipante;

    public ModeloDeCadastroDeUsuario(String nomeCompleto, String email, String senha, String emailDoParticipante) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.emailDoParticipante = emailDoParticipante;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmailDoParticipante() {
        return emailDoParticipante;
    }

    public void setEmailDoParticipante(String emailDoParticipante) {
        this.emailDoParticipante = emailDoParticipante;
    }
}
