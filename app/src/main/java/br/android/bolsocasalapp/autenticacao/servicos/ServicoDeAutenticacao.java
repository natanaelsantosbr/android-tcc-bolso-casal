package br.android.bolsocasalapp.autenticacao.servicos;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import br.android.bolsocasalapp.autenticacao.model.ModeloDeAutenticacao;
import br.android.bolsocasalapp.util.ConfiguracaoFirebase;
import br.android.bolsocasalapp.util.ExtensaoDeString;

public class ServicoDeAutenticacao implements IServicoDeAutenticacao {

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
}
