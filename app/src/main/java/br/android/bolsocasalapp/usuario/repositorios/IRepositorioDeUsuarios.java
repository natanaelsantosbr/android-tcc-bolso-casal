package br.android.bolsocasalapp.usuario.repositorios;

import br.android.bolsocasalapp.usuario.dominio.Usuario;

public interface IRepositorioDeUsuarios {
    void CadastrarNoAuth(String email, String senha);
    void CadastrarUsuarioNoBanco(Usuario usuario);
}
