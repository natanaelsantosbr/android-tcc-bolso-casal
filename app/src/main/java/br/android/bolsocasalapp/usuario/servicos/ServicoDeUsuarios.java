package br.android.bolsocasalapp.usuario.servicos;

import android.util.Base64;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.ParseException;

import br.android.bolsocasalapp.autenticacao.servicos.ICallbackAutenticar;
import br.android.bolsocasalapp.autenticacao.servicos.IServicoDeAutenticacao;
import br.android.bolsocasalapp.autenticacao.servicos.ServicoDeAutenticacao;
import br.android.bolsocasalapp.notificacoes.servicos.ICallbackToken;
import br.android.bolsocasalapp.notificacoes.servicos.IServicoDeNotificacao;
import br.android.bolsocasalapp.notificacoes.servicos.ServicoDeNotificacao;
import br.android.bolsocasalapp.usuario.dominio.Usuario;
import br.android.bolsocasalapp.usuario.model.ModeloDeCadastroDeUsuario;
import br.android.bolsocasalapp.autenticacao.servicos.ICallbackCadastrarNoAuth;
import br.android.bolsocasalapp.usuario.repositorios.ICallbackBuscarUsuarioNoBanco;
import br.android.bolsocasalapp.usuario.repositorios.IRepositorioDeUsuarios;
import br.android.bolsocasalapp.usuario.repositorios.RepositorioDeUsuarios;
import br.android.bolsocasalapp.helper.Base64Custom;
import br.android.bolsocasalapp.helper.ExtensaoDeString;

public class ServicoDeUsuarios implements IServicoDeUsuarios {

    private IServicoDeAutenticacao _servicoDeAutenticacao;
    private IRepositorioDeUsuarios _repositorioDeUsuarios;
    private IServicoDeNotificacao _servicoDeNotificacao;

    public ServicoDeUsuarios() {
        _servicoDeAutenticacao = new ServicoDeAutenticacao();
        _repositorioDeUsuarios = new RepositorioDeUsuarios();
        _servicoDeNotificacao = new ServicoDeNotificacao(this);
    }

    @Override
    public void Cadastrar(final ModeloDeCadastroDeUsuario modelo, final ICallbackCadastrar callback) {

        boolean validarNome = ExtensaoDeString.validarCampo(modelo.getNomeCompleto());
        boolean validarEmail = ExtensaoDeString.validarCampo(modelo.getEmail());
        boolean validarSenha = ExtensaoDeString.validarCampo(modelo.getSenha());
        boolean validarEmailDoParticipante = ExtensaoDeString.validarCampo(modelo.getEmailDoConjugue());

        if (!validarNome) {
            callback.onErro("Preencha o campo Nome");
            return;
        }

        if (!validarEmail) {
            callback.onErro("Preencha o campo E-mail");
            return;
        }

        if (!validarSenha) {
            callback.onErro("Preencha o campo Senha");
            return;
        }

        if (!validarEmailDoParticipante) {
            callback.onErro("Preencha o campo do email do cônjuge");
            return;
        }

        final String id = Base64Custom.codificarBase64(modelo.getEmail());

        String chaveDoConjuge = Base64Custom.codificarBase64(modelo.getEmailDoConjugue());

        _repositorioDeUsuarios.BuscarUsuarioNoBanco(chaveDoConjuge, new ICallbackBuscarUsuarioNoBanco() {
            @Override
            public void onSucesso(boolean retorno, Usuario usuarioRetorno) {
                boolean principal = true;

                if (usuarioRetorno != null)
                    principal = false;

                final boolean finalPrincipal = principal;

                _servicoDeNotificacao.RetornarToken(new ICallbackToken() {
                    @Override
                    public void onSucesso(String token) {

                        final Usuario usuario = new Usuario(id, modelo.getNomeCompleto(), modelo.getEmail(), modelo.getSenha(), modelo.getEmailDoConjugue(), finalPrincipal, token);

                        _servicoDeAutenticacao.Cadastrar(usuario.getNomeCompleto(), usuario.getEmail(), usuario.getSenha(), new ICallbackCadastrarNoAuth() {
                            @Override
                            public void onSucesso(FirebaseUser firebaseUser) {
                                _repositorioDeUsuarios.CadastrarUsuarioNoBanco(usuario);
                                callback.onSucesso(true);
                            }

                            @Override
                            public void onErro(String erro) {
                                callback.onErro(erro);
                            }
                        });
                    }
                });


            }

            @Override
            public void onErro(String mensagem) {
                callback.onErro(mensagem);

            }
        });


    }

    @Override
    public void BuscarUsuarioLogado(final ICallbackBuscarUsuarioLogado callback) {
        _servicoDeAutenticacao.VerificarSeEstaLogado(new ICallbackAutenticar() {
            @Override
            public void onSucesso(boolean retorno, FirebaseUser usuario) {
                String id = Base64Custom.codificarBase64(usuario.getEmail());

                _repositorioDeUsuarios.BuscarUsuarioNoBanco(id, new ICallbackBuscarUsuarioNoBanco() {
                    @Override
                    public void onSucesso(boolean retorno, Usuario usuario) {
                        callback.onSucesso(true, usuario);
                    }

                    @Override
                    public void onErro(String mensagem) {
                        callback.onErro(mensagem);
                    }
                });
            }

            @Override
            public void onErro(String mensagem) {

            }
        });

    }

    @Override
    public void BuscarDadosDoConjuge(final ICallbackBuscarUsuarioLogado callback) {
        _servicoDeAutenticacao.VerificarSeEstaLogado(new ICallbackAutenticar() {
            @Override
            public void onSucesso(boolean retorno, FirebaseUser usuario) {
                String id = Base64Custom.codificarBase64(usuario.getEmail());

                _repositorioDeUsuarios.BuscarUsuarioNoBanco(id, new ICallbackBuscarUsuarioNoBanco() {
                    @Override
                    public void onSucesso(boolean retorno, Usuario usuario) {
                        String idDoConjuge = Base64Custom.codificarBase64(usuario.getConjuge());

                        _repositorioDeUsuarios.BuscarConjugeNoBanco(idDoConjuge, new ICallbackBuscarUsuarioNoBanco() {
                            @Override
                            public void onSucesso(boolean retorno, Usuario usuario) {
                                callback.onSucesso(true, usuario);
                            }

                            @Override
                            public void onErro(String mensagem) {

                            }
                        });
                    }

                    @Override
                    public void onErro(String mensagem) {

                    }
                });
            }

            @Override
            public void onErro(String mensagem) {

            }
        });

    }

    @Override
    public void BuscarIdDoCasal(final ICallbackBuscarIdDoCasal callback) {
        this.BuscarUsuarioLogado(new ICallbackBuscarUsuarioLogado() {
            @Override
            public void onSucesso(boolean retorno, Usuario usuario) {
                if (retorno) {
                    String id = Base64Custom.codificarBase64(usuario.isPrincipal() ? usuario.getEmail() + usuario.getConjuge() : usuario.getConjuge() + usuario.getEmail());
                    callback.onSucesso(true, id);
                }
            }

            @Override
            public void onErro(String mensagem) {

            }
        });
    }
}
