package br.android.bolsocasalapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.List;

import br.android.bolsocasalapp.R;
import br.android.bolsocasalapp.autenticacao.servicos.ICallbackAutenticar;
import br.android.bolsocasalapp.autenticacao.servicos.IServicoDeAutenticacao;
import br.android.bolsocasalapp.autenticacao.servicos.ServicoDeAutenticacao;
import br.android.bolsocasalapp.despesas.activity.DespesaActivity;
import br.android.bolsocasalapp.despesas.dominio.Despesa;
import br.android.bolsocasalapp.despesas.model.AdapterDespesa;
import br.android.bolsocasalapp.despesas.servicos.ICallbackBuscarDespesasPorAnoMes;
import br.android.bolsocasalapp.despesas.servicos.IServicoDeDespesas;
import br.android.bolsocasalapp.despesas.servicos.ServicoDeDespesas;
import br.android.bolsocasalapp.usuario.activity.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private IServicoDeAutenticacao _servicoDeAutenticacao = new ServicoDeAutenticacao();
    private IServicoDeDespesas _servicoDeDespesas = new ServicoDeDespesas();
    private MaterialCalendarView calendarView;
    private RecyclerView recyclerDespesas;
    private TextView lblNomeDoUsuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("BolsoCasal");
        getSupportActionBar().setElevation(0);

        calendarView = findViewById(R.id.calendarView);
        recyclerDespesas = findViewById(R.id.recyclerDespesas);
        lblNomeDoUsuario = findViewById(R.id.lblNomeDoUsuario);
        configurarCalendarView();

        listarDespesas();

    }

    private void listarDespesas() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerDespesas.setLayoutManager(layoutManager);
        recyclerDespesas.setHasFixedSize(true);
    }

    public void configurarCalendarView() {

        CalendarDay dataAtual = calendarView.getCurrentDate();

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                limparListaDeDespesas();


                String mesSelecionado = String.format("%02d", (date.getMonth() ));
                mesSelecionado = mesSelecionado + "" + date.getYear();

                _servicoDeDespesas.BuscarDespesasPorAnoMes(mesSelecionado, new ICallbackBuscarDespesasPorAnoMes() {

                    @Override
                    public void onSucesso(boolean retorno, List<Despesa> despesas) {
                        if(retorno)
                        {
                            AdapterDespesa adapterDespesa = new AdapterDespesa(despesas, getApplicationContext());
                            recyclerDespesas.setAdapter(adapterDespesa);
                            adapterDespesa.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onErro(String mensagem) {

                    }
                });

            }
        });
    }

    private void limparListaDeDespesas() {
        List<Despesa> tempDespesas = new ArrayList<>();

        AdapterDespesa adapterDespesa = new AdapterDespesa(tempDespesas, getApplicationContext());
        recyclerDespesas.setAdapter(adapterDespesa);
        adapterDespesa.notifyDataSetChanged();
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
                else
                {
                    lblNomeDoUsuario.setText(usuario.getEmail());

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
        switch (item.getItemId()) {
            case R.id.menu_sair:
                _servicoDeAutenticacao.Deslogar(new ICallbackAutenticar() {
                    @Override
                    public void onSucesso(boolean retorno, FirebaseUser usuario) {
                        if (retorno) {
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
