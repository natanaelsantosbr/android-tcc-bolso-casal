package br.android.bolsocasalapp.usuario.repositorios;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.android.bolsocasalapp.usuario.dominio.Usuario;
import br.android.bolsocasalapp.helper.ConfiguracaoFirebase;

public class RepositorioDeUsuarios implements IRepositorioDeUsuarios {

    @Override
    public void CadastrarUsuarioNoBanco(Usuario usuario) {
        DatabaseReference firebase = ConfiguracaoFirebase.getDatabaseReference();
        firebase.child("usuarios").child(usuario.getId()).setValue(usuario);
    }

    @Override
    public void BuscarUsuarioNoBanco(String id, final ICallbackBuscarUsuarioNoBanco callback) {
        Log.d("BuscarUsuarioNoBanco", "BuscarUsuarioNoBanco: " + id);
        DatabaseReference firebase = ConfiguracaoFirebase.getDatabaseReference().child("usuarios").child(id);

        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    callback.onSucesso(true, usuario);
                } else {
                    callback.onSucesso(false, null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onErro(error.getMessage());
            }
        });
    }

    @Override
    public void BuscarConjugeNoBanco(String id, final ICallbackBuscarUsuarioNoBanco callback) {
        DatabaseReference firebase = ConfiguracaoFirebase.getDatabaseReference().child("usuarios").child(id);

        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    callback.onSucesso(true, usuario);
                } else {
                    callback.onSucesso(false, null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onErro(error.getMessage());
            }
        });
    }
}
