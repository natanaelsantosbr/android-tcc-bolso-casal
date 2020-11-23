package br.android.bolsocasalapp.usuario.servicos;

import br.android.bolsocasalapp.usuario.dominio.Conjuge;
import br.android.bolsocasalapp.usuario.dominio.Usuario;
import br.android.bolsocasalapp.usuario.model.ModeloDeCadastroDeUsuario;
import br.android.bolsocasalapp.usuario.repositorios.IRepositorioDeUsuarios;
import br.android.bolsocasalapp.usuario.repositorios.RepositorioDeUsuarios;
import br.android.bolsocasalapp.util.ExtensaoDeString;

public class ServicoDeUsuarios implements IServicoDeUsuarios {

    private IRepositorioDeUsuarios _repositorioDeUsuarios = new RepositorioDeUsuarios();


    @Override
    public boolean Cadastrar(ModeloDeCadastroDeUsuario modelo) {

        boolean validarNome = ExtensaoDeString.validarCampo(modelo.getNomeCompleto());
        boolean validarEmail = ExtensaoDeString.validarCampo(modelo.getEmail());
        boolean validarSenha = ExtensaoDeString.validarCampo(modelo.getSenha());
        boolean validarEmailDoParticipante = ExtensaoDeString.validarCampo(modelo.getEmailDoParticipante());

        if(!validarNome)
            return false;

        if(!validarEmail)
            return false;

        if(!validarSenha)
            return  false;

        if(!validarEmailDoParticipante)
            return false;

        Conjuge conjuge = new Conjuge(modelo.getEmailDoParticipante());
        Usuario usuario = new Usuario(modelo.getNomeCompleto(), modelo.getEmail(), modelo.getSenha(), conjuge );

        _repositorioDeUsuarios.Cadastrar(usuario);

        return  true;
    }
}
