package com.example.revendaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.revendaapp.R;
import com.example.revendaapp.activity.DetalhesActivity;
import com.example.revendaapp.model.Veiculo;

import java.util.List;

public class VeiculoAdapter extends RecyclerView.Adapter<VeiculoAdapter.VeiculoViewHolder> {

    private Context context;
    private List<Veiculo> listaVeiculos;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Veiculo veiculo);
        void onFavoritoClick(Veiculo veiculo);
        void onDetalhesClick(Veiculo veiculo);
    }

    public VeiculoAdapter(Context context, List<Veiculo> listaVeiculos, OnItemClickListener listener) {
        this.context = context;
        this.listaVeiculos = listaVeiculos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VeiculoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_veiculo, parent, false);
        return new VeiculoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VeiculoViewHolder holder, int position) {
        Veiculo veiculo = listaVeiculos.get(position);

        holder.tvModelo.setText(veiculo.getModelo());
        holder.tvAno.setText(String.valueOf(veiculo.getAno()));
        holder.tvPreco.setText(String.format("R$ %.2f", veiculo.getPreco()));

        // Configurar cliques
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(veiculo);
            }
        });

        holder.ivFavorito.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFavoritoClick(veiculo);
            }
        });

        holder.tvDetalhes.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalhesActivity.class);
            intent.putExtra("idVeiculo", veiculo.getIdVeiculo()); // Apenas o ID
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaVeiculos.size();
    }

    public void atualizarLista(List<Veiculo> novaLista) {
        listaVeiculos = novaLista;
        notifyDataSetChanged();
    }

    public static class VeiculoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto, ivFavorito;
        TextView tvModelo, tvAno, tvPreco, tvDetalhes;

        public VeiculoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            ivFavorito = itemView.findViewById(R.id.ivFavorito);
            tvModelo = itemView.findViewById(R.id.tvModelo);
            tvAno = itemView.findViewById(R.id.tvAno);
            tvPreco = itemView.findViewById(R.id.tvPreco);
            tvDetalhes = itemView.findViewById(R.id.tvDetalhes);
        }
    }
}