package br.android.bolsocasalapp.usuario.servicos;

import br.android.bolsocasalapp.usuario.model.ModeloDeCadastroDeUsuario;

public interface IServicoDeUsuarios {
    boolean Cadastrar(ModeloDeCadastroDeUsuario modelo);
}
