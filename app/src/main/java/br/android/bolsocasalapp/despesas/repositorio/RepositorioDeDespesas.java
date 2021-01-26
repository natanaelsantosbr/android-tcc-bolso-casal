package br.android.bolsocasalapp.despesas.repositorio;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.android.bolsocasalapp.despesas.dominio.Despesa;
import br.android.bolsocasalapp.helper.ConfiguracaoFirebase;

public class RepositorioDeDespesas implements IRepositorioDeDespesas {
    @Override
    public void CadastrarDespesaNoBanco(Despesa despesa) {
        DatabaseReference databaseReference = ConfiguracaoFirebase.getDatabaseReference();

        databaseReference.child("despesas")
                .child(despesa.getId())
                .child(despesa.getMesAno())
                .push()
                .setValue(despesa);
    }

    @Override
    public void BuscarDespesasPorMesAno(String id, String mesAno, final ICallbackBuscarDespesasPorMesAno callback)
    {
        DatabaseReference firebase = ConfiguracaoFirebase.getDatabaseReference();

        DatabaseReference despesas = firebase
                .child("despesas")
                .child(id)
                .child(mesAno);

        despesas.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Despesa> listaDeDespesas = new ArrayList<>();
                if(snapshot.exists())
                {
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        Despesa despesa = ds.getValue(Despesa.class);
                        listaDeDespesas.add(despesa);
                    }
                    callback.onSucesso(true, listaDeDespesas);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onErro(error.getMessage());
            }
        });
    }
}
