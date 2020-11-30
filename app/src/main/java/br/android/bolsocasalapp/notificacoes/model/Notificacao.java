package br.android.bolsocasalapp.notificacoes.model;

public class Notificacao {
    private String to;
    private NotificacaoItem notification;

    public Notificacao(String to, NotificacaoItem notification) {
        this.to = to;
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public NotificacaoItem getNotification() {
        return notification;
    }

    public void setNotification(NotificacaoItem notification) {
        this.notification = notification;
    }

    public Notificacao() {

    }
}
