package br.android.bolsocasalapp.autenticacao.servicos;

import com.google.firebase.auth.FirebaseUser;

public interface ICallbackAutenticar {
    void onSucesso(boolean retorno, FirebaseUser usuario);
    void onErro(String mensagem);
}
