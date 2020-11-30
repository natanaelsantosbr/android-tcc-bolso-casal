package br.android.bolsocasalapp.notificacoes.servicos.api;

import br.android.bolsocasalapp.notificacoes.model.Notificacao;
import br.android.bolsocasalapp.notificacoes.model.NotificacaoItem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IServicoDeNotificacoesApi {

    @Headers(
            {
                    "Authorization:key=AAAA9GNb0YA:APA91bEOcfhB2v2FHtvFGEGa7NRqgQ3UZkFDvWC2DRk-H_G57Qm3QC0_AUUh9yhmS0Vy_Acwvxv0pWzTLE2blYTCaB5MSPc-KRpA7FkGMIThh1QCjEoO4Hxe45gPpHpFdsHaOOGEryEC",
                    "Content-Type:application/json"
            }
    )
    @POST("send")
    Call<Notificacao> enviarNotificacao(@Body Notificacao notificacao);
}
