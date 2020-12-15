package br.android.bolsocasalapp.despesas.dominio;

import java.math.BigDecimal;
import java.util.Date;

import br.android.bolsocasalapp.usuario.dominio.Usuario;

public class Despesa {
    private String id;
    private String nome;
    private String data;
    private String mesAno;
    private String valor;
    private Usuario usuario;

    public Despesa() {
    }

    public Despesa(String id, String nome, String data, String mesAno, String  valor, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.valor = valor;
        this.mesAno = mesAno;
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
