package br.android.bolsocasalapp.notificacoes.servicos;

import br.android.bolsocasalapp.despesas.dominio.Despesa;

public interface IServicoDeNotificacao {
    void Enviar(Despesa despesa);
}
