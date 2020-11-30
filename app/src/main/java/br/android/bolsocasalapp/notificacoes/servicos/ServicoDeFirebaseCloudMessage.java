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

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import br.android.bolsocasalapp.R;
import br.android.bolsocasalapp.activity.MainActivity;

public class ServicoDeFirebaseCloudMessage extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage notificacao) {
        super.onMessageReceived(notificacao);

        if(notificacao != null)
        {
            Log.d("onMessageReceived", "onMessageReceived: " + notificacao.getFrom());

            String titulo = notificacao.getNotification().getTitle();
            String corpo = notificacao.getNotification().getBody();

            enviarNotificacao(titulo, corpo);
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("onNewToken", "onNewToken: " + s);
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
}
