package br.android.bolsocasalapp.despesa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import br.android.bolsocasalapp.R;

public class DespesaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);

        getSupportActionBar().setTitle("Adicionar Despesa");

    }
}
