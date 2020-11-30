package br.android.bolsocasalapp.despesas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.android.material.textfield.TextInputEditText;

import br.android.bolsocasalapp.R;
import br.android.bolsocasalapp.activity.MainActivity;
import br.android.bolsocasalapp.despesas.model.ModeloDeCadastroDeDespesa;
import br.android.bolsocasalapp.despesas.servicos.ICallbackCadastrarDespesa;
import br.android.bolsocasalapp.despesas.servicos.IServicoDeDespesas;
import br.android.bolsocasalapp.despesas.servicos.ServicoDeDespesas;
import dmax.dialog.SpotsDialog;

public class DespesaActivity extends AppCompatActivity {
    private TextInputEditText txtDespesaDescricao;
    private TextInputEditText txtDespesaCategoria;
    private TextInputEditText txtDespesaData;
    private CurrencyEditText txtDespesaValor;

    private IServicoDeDespesas _servicoDeDespesas;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);

        _servicoDeDespesas = new ServicoDeDespesas();

        getSupportActionBar().setTitle("Adicionar Despesa");

        inicializarComponentes();

    }

    private void inicializarComponentes() {
        txtDespesaDescricao = findViewById(R.id.txtDespesaDescricao);
        txtDespesaCategoria = findViewById(R.id.txtDespesaCategoria);
        txtDespesaData = findViewById(R.id.txtDespesaData);
        txtDespesaValor = findViewById(R.id.txtDespesaValor);
    }

    public void cadastrarDespesa(View view) {
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Cadastrando...")
                .setCancelable(true)
                .build();

        dialog.show();

        ModeloDeCadastroDeDespesa modelo = new ModeloDeCadastroDeDespesa(txtDespesaDescricao.getText().toString(),
                txtDespesaCategoria.getText().toString(), txtDespesaData.getText().toString(), txtDespesaValor.getText().toString());

        _servicoDeDespesas.Cadastrar(modelo, new ICallbackCadastrarDespesa() {
            @Override
            public void onSucesso(boolean retorno) {
                Toast.makeText(DespesaActivity.this, "Despesa Cadastrada com Sucesso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DespesaActivity.this, MainActivity.class));
                dialog.dismiss();
                finish();
            }

            @Override
            public void onErro(String mensagem) {
                Toast.makeText(DespesaActivity.this, mensagem, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
