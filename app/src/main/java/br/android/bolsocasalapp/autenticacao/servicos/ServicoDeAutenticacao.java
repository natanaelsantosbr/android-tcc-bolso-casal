package br.android.bolsocasalapp.autenticacao.servicos;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import br.android.bolsocasalapp.autenticacao.model.ModeloDeAutenticacao;
import br.android.bolsocasalapp.helper.ConfiguracaoFirebase;
import br.android.bolsocasalapp.helper.ExtensaoDeString;
import br.android.bolsocasalapp.usuario.servicos.ICallbackAtualizarToken;
import br.android.bolsocasalapp.usuario.servicos.IServicoDeUsuarios;
import br.android.bolsocasalapp.usuario.servicos.ServicoDeUsuarios;

public class ServicoDeAutenticacao implements IServicoDeAutenticacao {

        @Override
    public void Cadastrar(final String nome, String email, String senha, final ICallbackCadastrarNoAuth callback) {
        final FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();
        firebaseAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Log.d("Cadastrar", "Cadastrar: " + nome);
                    UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                            .setDisplayName(nome)
                            .build();

                    final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                    firebaseUser.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                callback.onSucesso(firebaseUser);
                            }
                        }
                    });
                } else {
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onErro(e.getMessage());
                    }
                }
            }
        });
    }

    @Override
    public void Autenticar(ModeloDeAutenticacao modelo, final ICallbackAutenticar callback) {
        final FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();

        boolean validarEmail = ExtensaoDeString.validarCampo(modelo.getEmail());
        boolean validarSenha = ExtensaoDeString.validarCampo(modelo.getSenha());

        if (!validarEmail) {
            callback.onErro("Preencha o campo E-mail");
            return;
        }

        if (!validarSenha) {
            callback.onErro("Preencha o campo Senha");
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(modelo.getEmail(), modelo.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    callback.onSucesso(true, firebaseAuth.getCurrentUser());
                }
                else
                {
                    String excecao = "";

                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidUserException e)
                    {
                        excecao = "Usuário não está cadastrado";
                    }
                    catch (FirebaseAuthInvalidCredentialsException e)
                    {
                        excecao = "Senha não corresponde ao usuario cadastrado";
                    }
                    catch (Exception e)
                    {
                        excecao = "Erro ao logar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    callback.onErro(excecao);
                }
            }
        });
    }

    @Override
    public void VerificarSeEstaLogado(ICallbackAutenticar callback) {
        FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();

        if(firebaseAuth.getCurrentUser() != null)
        {
            callback.onSucesso(true, firebaseAuth.getCurrentUser());
            return;
        }

        callback.onSucesso(false,  null);
    }

    @Override
    public void Deslogar(ICallbackAutenticar callback) {
        FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();

        if(firebaseAuth.getCurrentUser() != null)
            firebaseAuth.signOut();

        callback.onSucesso(true, null);
    }
}
