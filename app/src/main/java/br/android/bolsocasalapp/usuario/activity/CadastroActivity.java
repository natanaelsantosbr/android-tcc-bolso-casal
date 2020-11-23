package br.android.bolsocasalapp.usuario.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.android.bolsocasalapp.R;
import br.android.bolsocasalapp.usuario.model.ModeloDeCadastroDeUsuario;
import br.android.bolsocasalapp.usuario.servicos.IServicoDeUsuarios;
import br.android.bolsocasalapp.usuario.servicos.ServicoDeUsuarios;
import br.android.bolsocasalapp.util.ExtensaoDeString;

public class CadastroActivity extends AppCompatActivity {
    private EditText txtCadastroNome;
    private EditText txtCadastroEmail;
    private EditText txtCadastroSenha;
    private EditText txtCadastroEmailParticipante;
    private IServicoDeUsuarios _servicoDeUsuario = new ServicoDeUsuarios();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }

    public void cadastrarUsuario(View view)
    {
        String nome = txtCadastroNome.getText().toString();
        String email = txtCadastroEmail.getText().toString();
        String senha = txtCadastroSenha.getText().toString();
        String emailDoParticipante = txtCadastroEmailParticipante.getText().toString();

        ModeloDeCadastroDeUsuario modelo =  new ModeloDeCadastroDeUsuario(nome, email, senha, emailDoParticipante);

        _servicoDeUsuario.Cadastrar(modelo);
    }
}
