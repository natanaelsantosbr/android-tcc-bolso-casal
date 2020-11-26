package br.android.bolsocasalapp.despesas.repositorio;

import com.google.firebase.database.DatabaseReference;

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
}
