package br.android.bolsocasalapp.despesas.model;

public class ModeloDeCadastroDeDespesa {
    private String Descricao;
    private String Data;
    private String Valor;

    public ModeloDeCadastroDeDespesa(String descricao, String data, String valor) {
        Descricao = descricao;
        Data = data;
        Valor = valor;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        Valor = valor;
    }
}
