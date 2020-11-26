package br.android.bolsocasalapp.despesas.servicos;

public interface ICallbackCadastrarDespesa {
    void onSucesso(boolean retorno);
    void onErro(String mensagem);
}
