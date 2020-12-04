package br.android.bolsocasalapp.despesas.servicos;

import br.android.bolsocasalapp.despesas.model.ModeloDeCadastroDeDespesa;

public interface IServicoDeDespesas {
    void Cadastrar(ModeloDeCadastroDeDespesa modelo, ICallbackCadastrarDespesa callback);

    void BuscarDespesasPorAnoMes(String mesAno, ICallbackBuscarDespesasPorAnoMes callback);


}
