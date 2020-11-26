package br.android.bolsocasalapp.usuario.repositorios;

import br.android.bolsocasalapp.usuario.dominio.Usuario;

public interface IRepositorioDeUsuarios {

    void CadastrarUsuarioNoBanco(Usuario usuario);

    void BuscarUsuarioNoBanco(String id,  ICallbackBuscarUsuarioNoBanco callback);
}
