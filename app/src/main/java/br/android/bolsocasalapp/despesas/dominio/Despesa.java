package br.android.bolsocasalapp.despesas.dominio;

import java.util.Date;

import br.android.bolsocasalapp.usuario.dominio.Usuario;

public class Despesa {
    private String id;
    private String nome;
    private String categoria;
    private String data;
    private String mesAno;
    private Double valor;
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Despesa(String id, String nome, String categoria, String data, String mesAno, Double valor, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.data = data;
        this.valor = valor;
        this.mesAno = mesAno;
        this.usuario = usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMesAno() {
        return mesAno;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
