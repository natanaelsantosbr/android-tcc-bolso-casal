package br.android.bolsocasalapp.despesas.servicos;

import br.android.bolsocasalapp.despesas.dominio.Despesa;
import br.android.bolsocasalapp.despesas.model.ModeloDeCadastroDeDespesa;
import br.android.bolsocasalapp.despesas.repositorio.IRepositorioDeDespesas;
import br.android.bolsocasalapp.despesas.repositorio.RepositorioDeDespesas;
import br.android.bolsocasalapp.helper.DateCustom;
import br.android.bolsocasalapp.helper.ExtensaoDeString;
import br.android.bolsocasalapp.usuario.dominio.Usuario;
import br.android.bolsocasalapp.usuario.servicos.ICallbackBuscarUsuarioLogado;
import br.android.bolsocasalapp.usuario.servicos.IServicoDeUsuarios;
import br.android.bolsocasalapp.usuario.servicos.ServicoDeUsuarios;
import br.android.bolsocasalapp.helper.Base64Custom;


public class ServicoDeDespesas implements IServicoDeDespesas {

    private IServicoDeUsuarios _servicoDeUsuarios = new ServicoDeUsuarios();

    private IRepositorioDeDespesas _repositorioDeDespesas = new RepositorioDeDespesas();

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
                String id = Base64Custom.codificarBase64(usuario.getEmail() + usuario.getConjuge());

                Despesa despesa = new Despesa(id, modelo.getDescricao(), modelo.getCategoria(), modelo.getData(), DateCustom.mesAnoDataEscolhida(modelo.getData()), Double.parseDouble(modelo.getValor()));
                _repositorioDeDespesas.CadastrarDespesaNoBanco(despesa);
                callback.onSucesso(true);
            }

            @Override
            public void onErro(String mensagem) {
                callback.onErro(mensagem);
            }
        });
    }
}
