package br.android.bolsocasalapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseUser;

import br.android.bolsocasalapp.R;
import br.android.bolsocasalapp.autenticacao.servicos.ICallbackAutenticar;
import br.android.bolsocasalapp.autenticacao.servicos.IServicoDeAutenticacao;
import br.android.bolsocasalapp.autenticacao.servicos.ServicoDeAutenticacao;
import br.android.bolsocasalapp.despesa.activity.DespesaActivity;
import br.android.bolsocasalapp.usuario.activity.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private IServicoDeAutenticacao _servicoDeAutenticacao = new ServicoDeAutenticacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("BolsoCasal");
    }

    @Override
    protected void onStart() {
        super.onStart();
        _servicoDeAutenticacao.VerificarSeEstaLogado(new ICallbackAutenticar() {
            @Override
            public void onSucesso(boolean retorno, FirebaseUser usuario) {
                if(!retorno)
                {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }

            @Override
            public void onErro(String mensagem) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_sair:
                _servicoDeAutenticacao.Deslogar(new ICallbackAutenticar() {
                    @Override
                    public void onSucesso(boolean retorno, FirebaseUser usuario) {
                        if(retorno)
                        {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();

                        }
                    }

                    @Override
                    public void onErro(String mensagem) {

                    }
                });
                break;
            case R.id.menu_adicionar_despesa:
                startActivity(new Intent(MainActivity.this, DespesaActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);

    }
}
