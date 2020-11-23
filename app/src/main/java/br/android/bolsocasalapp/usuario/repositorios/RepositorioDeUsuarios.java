package br.android.bolsocasalapp.usuario.repositorios;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import br.android.bolsocasalapp.usuario.dominio.Usuario;
import br.android.bolsocasalapp.util.ConfiguracaoFirebase;

public class RepositorioDeUsuarios implements IRepositorioDeUsuarios {
    @Override
    public void Cadastrar(final Usuario usuario) {
        FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAuth();

        firebaseAuth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //Cadastrar Usuario no Realtime Database

                    //Atualizar Nome no Auth

                }
            }
        });
    }
}
