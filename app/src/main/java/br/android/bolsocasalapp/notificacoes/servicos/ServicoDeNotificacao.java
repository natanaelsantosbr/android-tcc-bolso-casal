package br.android.bolsocasalapp.notificacoes.servicos;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import br.android.bolsocasalapp.despesas.dominio.Despesa;
import br.android.bolsocasalapp.notificacoes.model.Notificacao;
import br.android.bolsocasalapp.notificacoes.model.NotificacaoItem;
import br.android.bolsocasalapp.notificacoes.servicos.api.IServicoDeNotificacoesApi;
import br.android.bolsocasalapp.usuario.dominio.Usuario;
import br.android.bolsocasalapp.usuario.servicos.ICallbackBuscarUsuarioLogado;
import br.android.bolsocasalapp.usuario.servicos.IServicoDeUsuarios;
import br.android.bolsocasalapp.usuario.servicos.ServicoDeUsuarios;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicoDeNotificacao implements IServicoDeNotificacao{

    private IServicoDeUsuarios _servicoDeUsuarios;

    public ServicoDeNotificacao(IServicoDeUsuarios servicoDeUsuarios) {
        _servicoDeUsuarios = servicoDeUsuarios;
    }

    @Override
    public void Enviar(final Despesa despesa)
    {
        Log.d("enviarNotificacao", "enviarNotificacao: " + 1);

        _servicoDeUsuarios.BuscarDadosDoConjuge(new ICallbackBuscarUsuarioLogado() {
            @Override
            public void onSucesso(boolean retorno, Usuario usuario) {
                if(retorno)
                {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://fcm.googleapis.com/fcm/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    IServicoDeNotificacoesApi servico = retrofit.create(IServicoDeNotificacoesApi.class);

                    NotificacaoItem item = new NotificacaoItem( despesa.getUsuario().getNomeCompleto() + " adicionou uma nova despesa paga", "Adicionou a despesa " + despesa.getNome() + " no valor de " + despesa.getValor());

                    String token = "/token/" +  usuario.getToken();

                    Notificacao notificacao = new Notificacao(token, item);

                    Log.d("enviarNotificacao", "enviarNotificacao: " + token);
                    Log.d("enviarNotificacao", "enviarNotificacao: " + notificacao.toString());

                    Call<Notificacao> call = servico.enviarNotificacao(notificacao);

                    call.enqueue(new Callback<Notificacao>() {
                        @Override
                        public void onResponse(Call<Notificacao> call, Response<Notificacao> response) {
                            if(response.isSuccessful())
                            {
                                Log.d("enviarNotificacao", "enviarNotificacao: " + response.code());
                            }
                            else
                            {
                                Log.d("enviarNotificacao", "enviarNotificacao: " + 4);
                            }

                        }

                        @Override
                        public void onFailure(Call<Notificacao> call, Throwable t) {
                            Log.d("enviarNotificacao", "enviarNotificacao: " + 3);

                        }
                    });
                }
            }

            @Override
            public void onErro(String mensagem) {

            }
        });
    }

    @Override
    public void RetornarToken(final ICallbackToken callback) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        callback.onSucesso(task.getResult());
                    }
                });
    }
}
