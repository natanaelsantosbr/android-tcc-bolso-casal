package br.android.bolsocasalapp.autenticacao.servicos;

import br.android.bolsocasalapp.autenticacao.model.ModeloDeAutenticacao;

public interface IServicoDeAutenticacao {
    void Cadastrar(String email, String senha, ICallbackCadastrarNoAuth callback);

    void Autenticar(ModeloDeAutenticacao modelo, ICallbackAutenticar callback);
}
