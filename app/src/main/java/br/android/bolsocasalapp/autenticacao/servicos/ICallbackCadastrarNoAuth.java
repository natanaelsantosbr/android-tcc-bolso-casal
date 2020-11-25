package br.android.bolsocasalapp.autenticacao.servicos;

import com.google.firebase.auth.FirebaseUser;

public interface ICallbackCadastrarNoAuth {
    void onSucesso(FirebaseUser firebaseUser);
    void onErro(String erro);
}
