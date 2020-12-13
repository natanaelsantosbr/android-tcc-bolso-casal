package br.android.bolsocasalapp.usuario.repositorios;

public interface ICallbackAtualizarToken {
    void onSucesso(boolean retorno) ;
    void onErro(String mensagem);
}
