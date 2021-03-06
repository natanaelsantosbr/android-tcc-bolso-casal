package br.android.bolsocasalapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
    private IServicoDeAutenticacao _servicoDeAutenticacao = new ServicoDeAutenticacao();
    private IServicoDeDespesas _servicoDeDespesas = new ServicoDeDespesas();
    private MaterialCalendarView calendarView;
    private RecyclerView recyclerDespesas;
    private TextView lblNomeDoUsuario,lblValorDoMes;
    private AlertDialog dialog;
    private ProgressBar progressBarDespesa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("BolsoCasal");
        getSupportActionBar().setElevation(0);

        calendarView = findViewById(R.id.calendarView);
        recyclerDespesas = findViewById(R.id.recyclerDespesas);
        lblNomeDoUsuario = findViewById(R.id.lblNomeDoUsuario);
        lblValorDoMes = findViewById(R.id.lblValorDoMes);
        progressBarDespesa = findViewById(R.id.progressBarDespesa);
        configurarCalendarView();

        configurarRecyclerView();
    }

    private void listarDespesaDoMesAtual() {
        progressBarDespesa.setVisibility(View.VISIBLE);
        CalendarDay dataAtual = calendarView.getCurrentDate();

        String mes = String.format("%02d", dataAtual.getMonth());
        String ano = String.valueOf(dataAtual.getYear());

        String mesAtual = mes  + ano;

        buscarDespesasPorMesAno(mesAtual);
    }

    private void buscarDespesasPorMesAno(final String mesAtual) {
        _servicoDeDespesas.BuscarDespesasPorAnoMes(mesAtual, new ICallbackBuscarDespesasPorAnoMes() {

            @Override
            public void onSucesso(boolean retorno, List<Despesa> despesas) {
                if (retorno) {
                    AdapterDespesa adapterDespesa = new AdapterDespesa(despesas, getApplicationContext());
                    recyclerDespesas.setAdapter(adapterDespesa);
                    adapterDespesa.notifyDataSetChanged();

                    double valorTotal = 0;


                    for (Despesa despesa : despesas)
                    {
                        double valorDaDespesa = Double.parseDouble(despesa.getValor());
                        valorTotal += valorDaDespesa;
                    }

                    lblValorDoMes.setText(NumberFormat.getCurrencyInstance().format(valorTotal));
                }
                progressBarDespesa.setVisibility(View.GONE);
            }

            @Override
            public void onErro(String mensagem) {
                progressBarDespesa.setVisibility(View.GONE);
            }
        });
    }

    private void configurarRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerDespesas.setLayoutManager(layoutManager);
        recyclerDespesas.setHasFixedSize(true);
    }

    public void configurarCalendarView() {

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                limparListaDeDespesas();
                lblValorDoMes.setText("R$ 0,00");
                progressBarDespesa.setVisibility(View.VISIBLE);

                String mesSelecionado = String.format("%02d", (date.getMonth() ));
                mesSelecionado = mesSelecionado + "" + date.getYear();

                buscarDespesasPorMesAno(mesSelecionado);

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
        verificarSeEstaLogado();
        listarDespesaDoMesAtual();

    }

    private void verificarSeEstaLogado() {
        _servicoDeAutenticacao.VerificarSeEstaLogado(new ICallbackAutenticar() {
            @Override
            public void onSucesso(boolean retorno, FirebaseUser usuario) {
                if(!retorno)
                {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                else
                {
                    lblNomeDoUsuario.setText(usuario.getDisplayName());
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

    private void fecharDialog() {
        dialog.dismiss();
    }

    private void abrirDialog() {
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Autenticando...")
                .setCancelable(true)
                .build();

        dialog.show();
    }
}
