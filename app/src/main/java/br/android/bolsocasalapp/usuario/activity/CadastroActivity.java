package br.android.bolsocasalapp.usuario.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import br.android.bolsocasalapp.R;
import br.android.bolsocasalapp.activity.MainActivity;
import br.android.bolsocasalapp.usuario.model.ModeloDeCadastroDeUsuario;
import br.android.bolsocasalapp.usuario.servicos.ICallbackCadastrar;
import br.android.bolsocasalapp.usuario.servicos.IServicoDeUsuarios;
import br.android.bolsocasalapp.usuario.servicos.ServicoDeUsuarios;
import dmax.dialog.SpotsDialog;

public class CadastroActivity extends AppCompatActivity {
    private EditText txtCadastroNome;
    private EditText txtCadastroEmail;
    private EditText txtCadastroSenha;
    private EditText txtCadastroEmailConjuge;
    private IServicoDeUsuarios _servicoDeUsuario = new ServicoDeUsuarios();
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializarComponentes();

    }

    public void cadastrarUsuario(View view)
    {
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Cadastrando...")
                .setCancelable(true)
                .build();

        dialog.show();

        String nome = txtCadastroNome.getText().toString();
        String email = txtCadastroEmail.getText().toString();
        String senha = txtCadastroSenha.getText().toString();
        String emailDoParticipante = txtCadastroEmailConjuge.getText().toString();

        ModeloDeCadastroDeUsuario modelo =  new ModeloDeCadastroDeUsuario(nome, email, senha, emailDoParticipante);

        _servicoDeUsuario.Cadastrar(modelo, new ICallbackCadastrar() {
            @Override
            public void onSucesso(boolean retorno) {
                if(retorno)
                {
                    Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CadastroActivity.this, MainActivity.class));
                    dialog.dismiss();
                }
            }

            @Override
            public void onErro(String mensagem) {
                Toast.makeText(CadastroActivity.this, "Erro: " + mensagem, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    public void abrirTelaDeLogin(View view)
    {
        startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
        finish();
    }

    private void inicializarComponentes() {
        txtCadastroNome = findViewById(R.id.txtCadastroNome);
        txtCadastroEmail = findViewById(R.id.txtCadastroEmail);
        txtCadastroEmailConjuge = findViewById(R.id.txtCadastroEmailConjuge);
        txtCadastroSenha = findViewById(R.id.txtCadastroSenha);
    }


}
