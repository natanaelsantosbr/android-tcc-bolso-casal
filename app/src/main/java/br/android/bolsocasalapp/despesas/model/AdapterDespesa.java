package br.android.bolsocasalapp.despesas.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.android.bolsocasalapp.R;
import br.android.bolsocasalapp.despesas.dominio.Despesa;

public class AdapterDespesa extends RecyclerView.Adapter<AdapterDespesa.MovimentacaoViewHolder> {
    List<Despesa> despesas;
    Context context;

    public AdapterDespesa(List<Despesa> despesas, Context context) {
        this.despesas = despesas;
        this.context = context;
    }

    @NonNull
    @Override
    public MovimentacaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_despesa, parent, false);
        return new AdapterDespesa.MovimentacaoViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MovimentacaoViewHolder holder, int position) {
        Despesa despesa = despesas.get(position);

        holder.titulo.setText(despesa.getNome());
        holder.valor.setText(despesa.getValorFormatado());
        holder.nome.setText(despesa.getUsuario().getNomeCompleto());
    }

    @Override
    public int getItemCount() {
        return despesas.size();
    }

    public class MovimentacaoViewHolder extends RecyclerView.ViewHolder
    {
        TextView titulo, valor, nome;


        public MovimentacaoViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.txtAdapterTitulo);
            valor = itemView.findViewById(R.id.txtAdapterValor);
            nome = itemView.findViewById(R.id.txtAdapterNome);
        }
    }
}
