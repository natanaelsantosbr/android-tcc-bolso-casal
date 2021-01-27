package br.android.bolsocasalapp.despesas.repositorio;

import br.android.bolsocasalapp.despesas.dominio.Despesa;

public interface IRepositorioDeDespesas {
    void CadastrarDespesaNoBanco(Despesa despesa);
    void BuscarDespesasPorMesAno(String id,  String mesAno, ICallbackBuscarDespesasPorMesAno callback);
    void AtualizarDespesa(Despesa despesa);

}
