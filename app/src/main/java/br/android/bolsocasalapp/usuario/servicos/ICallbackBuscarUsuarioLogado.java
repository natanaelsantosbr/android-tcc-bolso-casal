package br.android.bolsocasalapp.usuario.servicos;

import java.text.ParseException;

import br.android.bolsocasalapp.usuario.dominio.Usuario;

public interface ICallbackBuscarUsuarioLogado {
    void onSucesso(boolean retorno, Usuario usuario);
    void onErro(String mensagem);
}
