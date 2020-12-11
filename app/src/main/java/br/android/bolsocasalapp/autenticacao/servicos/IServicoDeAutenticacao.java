package br.android.bolsocasalapp.autenticacao.servicos;

import br.android.bolsocasalapp.autenticacao.model.ModeloDeAutenticacao;
import br.android.bolsocasalapp.usuario.servicos.ICallbackCadastrar;

public interface IServicoDeAutenticacao {
    void Cadastrar(String nome,  String email, String senha, ICallbackCadastrarNoAuth callback);

    void Autenticar(ModeloDeAutenticacao modelo, ICallbackAutenticar callback);

    void VerificarSeEstaLogado(ICallbackAutenticar callback);

    void Deslogar(ICallbackAutenticar callback);
}
