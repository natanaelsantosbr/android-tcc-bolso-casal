package br.android.bolsocasalapp.despesas.repositorio;

import java.util.List;

import br.android.bolsocasalapp.despesas.dominio.Despesa;

public interface ICallbackBuscarDespesasPorMesAno {
    void onSucesso(boolean retorno, List<Despesa> despesas) ;
    void onErro(String mensagem);
}
