package br.android.bolsocasalapp.usuario.repositorios;

import java.text.ParseException;

import br.android.bolsocasalapp.usuario.dominio.Usuario;

public interface ICallbackBuscarUsuarioNoBanco {
    void onSucesso(boolean retorno,  Usuario usuario) ;
    void onErro(String mensagem);
}
