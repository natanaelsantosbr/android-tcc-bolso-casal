package br.android.bolsocasalapp.usuario.servicos;

public interface ICallbackBuscarIdDoCasal {
    void onSucesso(boolean retorno,  String idDoCasal) ;
    void onErro(String mensagem);
}
