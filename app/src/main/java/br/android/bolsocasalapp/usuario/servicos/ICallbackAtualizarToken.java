package br.android.bolsocasalapp.usuario.servicos;

public interface ICallbackAtualizarToken {
    void onSucesso(boolean retorno, String token);
    void onErro(String email);
}
