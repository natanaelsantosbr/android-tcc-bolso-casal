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
import br.android.bolsocasalapp.notificacoes.model.Notificacao;
import br.android.bolsocasalapp.notificacoes.model.NotificacaoItem;
import br.android.bolsocasalapp.notificacoes.servicos.api.IServicoDeNotificacoesApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicoDeNotificacoes extends FirebaseMessagingService
        implements IServicoDeNotificacoes
{

    @Override
    public void onMessageReceived(@NonNull RemoteMessage notificacao) {
        super.onMessageReceived(notificacao);

        if(notificacao != null)
        {
            String titulo = notificacao.getNotification().getTitle();
            String corpo = notificacao.getNotification().getBody();

            enviarNotificacao(titulo, corpo);
        }
    }

    private void enviarNotificacao(String titulo, String corpo)
    {
        String canal = getString(R.string.default_notification_channel_id);
        Uri uriSom = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificacao = new NotificationCompat.Builder(this, canal)
                .setContentTitle(titulo)
                .setContentText(corpo)
                .setSound(uriSom)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_notificacao_branco_24dp)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(canal, "canal", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificacao.build());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("onNewToken", "onNewToken: " + s);
    }


    @Override
    public void Enviar(Notificacao notificacao)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/fcm/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IServicoDeNotificacoesApi servico = retrofit.create(IServicoDeNotificacoesApi.class);

        Call<Notificacao> call = servico.enviarNotificacao(notificacao);

        call.enqueue(new Callback<Notificacao>() {
            @Override
            public void onResponse(Call<Notificacao> call, Response<Notificacao> response) {
                Toast.makeText(ServicoDeNotificacoes.this, "Mensagem enviada com sucesso", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Notificacao> call, Throwable t) {

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

