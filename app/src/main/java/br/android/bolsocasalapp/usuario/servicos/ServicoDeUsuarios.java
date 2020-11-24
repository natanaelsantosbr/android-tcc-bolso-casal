package br.android.bolsocasalapp.usuario.servicos;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import br.android.bolsocasalapp.usuario.dominio.Conjuge;
import br.android.bolsocasalapp.usuario.dominio.Usuario;
import br.android.bolsocasalapp.usuario.model.ModeloDeCadastroDeUsuario;
import br.android.bolsocasalapp.usuario.repositorios.ICallbackCadastrarNoAuth;
import br.android.bolsocasalapp.usuario.repositorios.IRepositorioDeUsuarios;
import br.android.bolsocasalapp.usuario.repositorios.RepositorioDeUsuarios;
import br.android.bolsocasalapp.util.ExtensaoDeString;

public class ServicoDeUsuarios implements IServicoDeUsuarios {

    private IRepositorioDeUsuarios _repositorioDeUsuarios = new RepositorioDeUsuarios();


    @Override
    public void Cadastrar(ModeloDeCadastroDeUsuario modelo, final ICallbackCadastrar callback) {

        boolean validarNome = ExtensaoDeString.validarCampo(modelo.getNomeCompleto());
        boolean validarEmail = ExtensaoDeString.validarCampo(modelo.getEmail());
        boolean validarSenha = ExtensaoDeString.validarCampo(modelo.getSenha());
        boolean validarEmailDoParticipante = ExtensaoDeString.validarCampo(modelo.getEmailDoParticipante());

        if (!validarNome) {
            callback.onErro("Preencha o campo Nome");
            return;
        }

        if (!validarEmail) {
            callback.onErro("Preencha o campo E-mail");
            return;
        }

        if (!validarSenha) {
            callback.onErro("Preencha o campo Senha");
            return;
        }

        if (!validarEmailDoParticipante) {
            callback.onErro("Preencha o campo do email do cônjuge");
            return;
        }

        Conjuge conjuge = new Conjuge(modelo.getEmailDoParticipante());
        final Usuario usuario = new Usuario(modelo.getNomeCompleto(), modelo.getEmail(), modelo.getSenha(), conjuge);

        _repositorioDeUsuarios.CadastrarNoAuth(usuario.getEmail(), usuario.getSenha(), new ICallbackCadastrarNoAuth() {
            @Override
            public void onSucesso(FirebaseUser firebaseUser) {
                _repositorioDeUsuarios.CadastrarUsuarioNoBanco(usuario);
                callback.onSucesso(true);
            }

            @Override
            public void onErro(String erro) {
                Log.d("ServicoDeUsuarios", "onErro: " + erro);
                callback.onErro(erro);
            }
        });
    }
}
