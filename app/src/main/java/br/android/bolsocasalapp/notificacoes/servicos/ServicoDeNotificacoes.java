package br.android.bolsocasalapp.notificacoes.servicos;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import br.android.bolsocasalapp.R;
import br.android.bolsocasalapp.activity.MainActivity;
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

public class ServicoDeNotificacoes implements IServicoDeNotificacoes
{
    private IServicoDeUsuarios _servicoDeUsuarios;


    public ServicoDeNotificacoes() {
        _servicoDeUsuarios = new ServicoDeUsuarios();
    }

    public void Enviar(final Despesa despesa)
    {
        _servicoDeUsuarios.BuscarUsuarioLogado(new ICallbackBuscarUsuarioLogado() {
            @Override
            public void onSucesso(boolean retorno, Usuario usuario) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://fcm.googleapis.com/fcm/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                IServicoDeNotificacoesApi servico = retrofit.create(IServicoDeNotificacoesApi.class);

                NotificacaoItem item = new NotificacaoItem( despesa.getUsuario().getNomeCompleto() + "adicionou uma nova despesa", "Adicionou a despesa " + despesa.getNome() + " no valor de " + despesa.getValor());

                Notificacao notificacao = new Notificacao(despesa.getUsuario().getToken(), item);

                Call<Notificacao> call = servico.enviarNotificacao(notificacao);

                call.enqueue(new Callback<Notificacao>() {
                    @Override
                    public void onResponse(Call<Notificacao> call, Response<Notificacao> response) {
                    }

                    @Override
                    public void onFailure(Call<Notificacao> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onErro(String mensagem) {

            }
        });
    }

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
