package br.android.bolsocasalapp.notificacoes.model;

public class NotificacaoItem {
    private String title;
    private String body;

    public NotificacaoItem(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public NotificacaoItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
