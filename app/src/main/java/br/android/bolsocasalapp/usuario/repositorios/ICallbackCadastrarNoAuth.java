package br.android.bolsocasalapp.usuario.repositorios;

import com.google.firebase.auth.FirebaseUser;

public interface ICallbackCadastrarNoAuth {
    void onSucesso(FirebaseUser firebaseUser);
    void onErro(String erro);
}
