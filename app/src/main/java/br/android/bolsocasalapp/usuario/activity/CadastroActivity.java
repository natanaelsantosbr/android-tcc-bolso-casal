package br.android.bolsocasalapp.usuario.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import br.android.bolsocasalapp.R;
import br.android.bolsocasalapp.activity.MainActivity;
import br.android.bolsocasalapp.usuario.model.ModeloDeCadastroDeUsuario;
import br.android.bolsocasalapp.usuario.servicos.ICallbackCadastrar;
import br.android.bolsocasalapp.usuario.servicos.IServicoDeUsuarios;
import br.android.bolsocasalapp.usuario.servicos.ServicoDeUsuarios;

public class CadastroActivity extends AppCompatActivity {
    private TextInputEditText txtCadastroNome;
    private TextInputEditText txtCadastroEmail;
    private TextInputEditText txtCadastroSenha;
    private TextInputEditText txtCadastroEmailConjuge;
    private IServicoDeUsuarios _servicoDeUsuario = new ServicoDeUsuarios();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializarComponentes();

    }

    public void cadastrarUsuario(View view)
    {
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
                }
            }

            @Override
            public void onErro(String mensagem) {
                Toast.makeText(CadastroActivity.this, "Erro: " + mensagem, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inicializarComponentes() {
        txtCadastroNome = findViewById(R.id.txtCadastroNome);
        txtCadastroEmail = findViewById(R.id.txtCadastroEmail);
        txtCadastroEmailConjuge = findViewById(R.id.txtCadastroEmailConjuge);
        txtCadastroSenha = findViewById(R.id.txtCadastroSenha);
    }


}
