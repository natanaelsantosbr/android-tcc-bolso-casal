package br.android.bolsocasalapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

import br.android.bolsocasalapp.R;
import br.android.bolsocasalapp.autenticacao.servicos.ICallbackAutenticar;
import br.android.bolsocasalapp.autenticacao.servicos.IServicoDeAutenticacao;
import br.android.bolsocasalapp.autenticacao.servicos.ServicoDeAutenticacao;
import br.android.bolsocasalapp.usuario.activity.CadastroActivity;
import br.android.bolsocasalapp.usuario.activity.LoginActivity;

public class TutorialActivity extends IntroActivity {

    private IServicoDeAutenticacao _servicoDeAutenticacao = new ServicoDeAutenticacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_1)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_2)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_3)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_4)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_cadastro)
                .canGoForward(false)
                .build());
    }

    public void btnEntrar(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void btnCadastrar(View view)
    {
        startActivity(new Intent(this, CadastroActivity.class));
    }

    private void verificarSeEstaLogado() {
        _servicoDeAutenticacao.VerificarSeEstaLogado(new ICallbackAutenticar() {
            @Override
            public void onSucesso(boolean retorno, FirebaseUser usuario) {
                if(retorno)
                {
                    startActivity(new Intent(TutorialActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onErro(String mensagem) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
       verificarSeEstaLogado();
    }
}
