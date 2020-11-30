package br.android.bolsocasalapp.notificacoes.servicos;


import br.android.bolsocasalapp.notificacoes.model.Notificacao;

public interface IServicoDeNotificacoes
{
    void Enviar(Notificacao notificacao);

    void RetornarToken(ICallbackToken callback);

}
