package br.android.bolsocasalapp.usuario.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrinterId;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import br.android.bolsocasalapp.R;
import br.android.bolsocasalapp.activity.MainActivity;
import br.android.bolsocasalapp.autenticacao.model.ModeloDeAutenticacao;
import br.android.bolsocasalapp.autenticacao.servicos.ICallbackAutenticar;
import br.android.bolsocasalapp.autenticacao.servicos.IServicoDeAutenticacao;
import br.android.bolsocasalapp.autenticacao.servicos.ServicoDeAutenticacao;
import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText txtLoginEmail;
    private TextInputEditText txtLoginSenha;
    private IServicoDeAutenticacao _servicoDeAutenticacao = new ServicoDeAutenticacao();
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarComponentes();
    }

    public void entrar(View view)
    {
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Autenticando...")
                .setCancelable(true)
                .build();

        dialog.show();
        ModeloDeAutenticacao modelo = new ModeloDeAutenticacao(txtLoginEmail.getText().toString(), txtLoginSenha.getText().toString());

        _servicoDeAutenticacao.Autenticar(modelo, new ICallbackAutenticar() {
            @Override
            public void onSucesso(boolean retorno, FirebaseUser usuario) {
                if(retorno)
                {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    Toast.makeText(LoginActivity.this, usuario.getEmail(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onErro(String mensagem) {
                Toast.makeText(LoginActivity.this, mensagem, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    public void abrirTelaDeCadastro(View view)
    {
        startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
    }


    private void inicializarComponentes() {
        txtLoginEmail = findViewById(R.id.txtLoginEmail);
        txtLoginSenha = findViewById(R.id.txtLoginSenha);
    }
}
