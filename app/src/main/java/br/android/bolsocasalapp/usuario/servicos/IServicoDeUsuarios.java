package br.android.bolsocasalapp.usuario.servicos;

import br.android.bolsocasalapp.usuario.model.ModeloDeCadastroDeUsuario;

public interface IServicoDeUsuarios {
    void Cadastrar(ModeloDeCadastroDeUsuario modelo, ICallbackCadastrar callback);

    void BuscarUsuarioLogado(ICallbackBuscarUsuarioLogado callback);

    void BuscarDadosDoConjuge(ICallbackBuscarUsuarioLogado callback);
}
