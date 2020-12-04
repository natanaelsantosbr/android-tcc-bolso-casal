package br.android.bolsocasalapp.despesas.servicos;

import java.util.List;

import br.android.bolsocasalapp.despesas.dominio.Despesa;

public interface ICallbackBuscarDespesasPorAnoMes {
    void onSucesso(boolean retorno, List<Despesa> despesas);
    void onErro(String mensagem);
}
