package br.android.bolsocasalapp.despesas.servicos;

import android.util.Log;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import br.android.bolsocasalapp.despesas.dominio.Despesa;
import br.android.bolsocasalapp.despesas.model.ModeloDeCadastroDeDespesa;
import br.android.bolsocasalapp.despesas.repositorio.ICallbackBuscarDespesasPorMesAno;
import br.android.bolsocasalapp.despesas.repositorio.IRepositorioDeDespesas;
import br.android.bolsocasalapp.despesas.repositorio.RepositorioDeDespesas;
import br.android.bolsocasalapp.helper.DateCustom;
import br.android.bolsocasalapp.helper.ExtensaoDeString;
import br.android.bolsocasalapp.notificacoes.servicos.IServicoDeNotificacao;
import br.android.bolsocasalapp.notificacoes.servicos.IServicoDeNotificacoes;
import br.android.bolsocasalapp.notificacoes.servicos.ServicoDeNotificacao;
import br.android.bolsocasalapp.notificacoes.servicos.ServicoDeNotificacoes;
import br.android.bolsocasalapp.usuario.dominio.Usuario;
import br.android.bolsocasalapp.usuario.servicos.ICallbackBuscarUsuarioLogado;
import br.android.bolsocasalapp.usuario.servicos.IServicoDeUsuarios;
import br.android.bolsocasalapp.usuario.servicos.ServicoDeUsuarios;
import br.android.bolsocasalapp.helper.Base64Custom;


public class ServicoDeDespesas implements IServicoDeDespesas {

    private IServicoDeUsuarios _servicoDeUsuarios;
    private IRepositorioDeDespesas _repositorioDeDespesas;
    private IServicoDeNotificacao _servicoDeNotificacoes;

    public ServicoDeDespesas() {
        this._servicoDeUsuarios = new ServicoDeUsuarios();
        this._repositorioDeDespesas = new RepositorioDeDespesas();
        this._servicoDeNotificacoes = new ServicoDeNotificacao(this._servicoDeUsuarios);
    }

    @Override
    public void Cadastrar(final ModeloDeCadastroDeDespesa modelo, final ICallbackCadastrarDespesa callback) {
        boolean validarDescricao = ExtensaoDeString.validarCampo(modelo.getDescricao());
        boolean validarCategoria = ExtensaoDeString.validarCampo(modelo.getCategoria());
        boolean validarData = ExtensaoDeString.validarCampo(modelo.getData());
        boolean validarValor = ExtensaoDeString.validarCampo(modelo.getValor());

        if (!validarDescricao) {
            callback.onErro("Preencha a Descrição");
            return;
        }

        if (!validarCategoria) {
            callback.onErro("Preencha a Categoria");
            return;
        }

        if (!validarData) {
            callback.onErro("Preencha a Data");
            return;
        }

        if (!validarValor) {
            callback.onErro("Preencha o Valor");
            return;
        }
        _servicoDeUsuarios.BuscarUsuarioLogado(new ICallbackBuscarUsuarioLogado() {
            @Override
            public void onSucesso(boolean retorno, Usuario usuario) {
                String id = Base64Custom.codificarBase64(usuario.isPrincipal() ? usuario.getEmail() + usuario.getConjuge() : usuario.getConjuge() + usuario.getEmail());
                modelo.setValor(String.valueOf(ExtensaoDeString.ConverterRealParaString(modelo.getValor())));

                Despesa despesa = new Despesa(id, modelo.getDescricao(), modelo.getCategoria(), modelo.getData(), DateCustom.mesAnoDataEscolhida(modelo.getData()), modelo.getValor(), usuario);

                _repositorioDeDespesas.CadastrarDespesaNoBanco(despesa);

                _servicoDeNotificacoes.Enviar(despesa);

                callback.onSucesso(true);
            }

            @Override
            public void onErro(String mensagem) {
                callback.onErro(mensagem);
            }
        });
    }

    @Override
    public void BuscarDespesasPorAnoMes(String mesAno, final ICallbackBuscarDespesasPorAnoMes callback) {

        String idDoCasal = "";
        mesAno = "";

        _repositorioDeDespesas.BuscarDespesasPorMesAno(idDoCasal, mesAno, new ICallbackBuscarDespesasPorMesAno() {
            @Override
            public void onSucesso(boolean retorno, List<Despesa> despesas) {
                if(retorno)
                {
                    callback.onSucesso(true, despesas);
                }
            }

            @Override
            public void onErro(String mensagem) {
                callback.onErro(mensagem);
            }
        });
    }
}
