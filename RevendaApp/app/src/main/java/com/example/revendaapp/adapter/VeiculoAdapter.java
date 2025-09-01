package com.example.revendaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.revendaapp.R;
import com.example.revendaapp.model.Veiculo;

import java.util.List;

public class VeiculoAdapter extends RecyclerView.Adapter<VeiculoAdapter.VeiculoViewHolder> {

    private Context context;
    private List<Veiculo> listaVeiculos;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Veiculo veiculo);
        void onFavoritoClick(Veiculo veiculo);
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

        // Aqui você carregaria a imagem usando Glide/Picasso
        // Glide.with(context).load(veiculo.getFotoCapa()).into(holder.ivFoto);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(veiculo));
        holder.ivFavorito.setOnClickListener(v -> listener.onFavoritoClick(veiculo));
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
        ImageView ivFoto;
        TextView tvModelo;
        TextView tvAno;
        TextView tvPreco;
        ImageView ivFavorito;

        public VeiculoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            tvModelo = itemView.findViewById(R.id.tvModelo);
            tvAno = itemView.findViewById(R.id.tvAno);
            tvPreco = itemView.findViewById(R.id.tvPreco);
            ivFavorito = itemView.findViewById(R.id.ivFavorito);
        }
    }
}