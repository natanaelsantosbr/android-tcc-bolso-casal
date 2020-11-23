package br.android.bolsocasalapp.view.cadastroDeUsuario;

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
}
