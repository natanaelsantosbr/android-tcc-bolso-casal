package br.android.bolsocasalapp.usuario.repositorios;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import br.android.bolsocasalapp.autenticacao.servicos.ICallbackCadastrarNoAuth;
import br.android.bolsocasalapp.usuario.dominio.Usuario;
import br.android.bolsocasalapp.util.ConfiguracaoFirebase;

public class RepositorioDeUsuarios implements IRepositorioDeUsuarios {

    @Override
    public void CadastrarUsuarioNoBanco(Usuario usuario) {
        DatabaseReference firebase = ConfiguracaoFirebase.getDatabaseReference();
        firebase.child("usuarios").push().setValue(usuario);
    }
}
