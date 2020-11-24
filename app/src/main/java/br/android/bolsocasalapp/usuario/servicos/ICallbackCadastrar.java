package br.android.bolsocasalapp.usuario.servicos;

public interface ICallbackCadastrar {
    void onSucesso(boolean retorno);
    void onErro(String mensagem);
}
