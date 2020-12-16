package br.android.bolsocasalapp.usuario.servicos;

public interface ICallbackToken {
    void onSucesso(String token);
    void onErro(String mensagem);
}
