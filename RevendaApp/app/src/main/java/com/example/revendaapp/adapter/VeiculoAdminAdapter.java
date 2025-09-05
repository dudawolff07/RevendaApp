package com.example.revendaapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.revendaapp.R;
import com.example.revendaapp.activity.DetalhesActivity;
import com.example.revendaapp.activity.EditarVeiculoActivity;
import com.example.revendaapp.database.VeiculoDAO;
import com.example.revendaapp.model.Veiculo;

import java.util.List;

public class VeiculoAdminAdapter extends RecyclerView.Adapter<VeiculoAdminAdapter.VeiculoAdminViewHolder> {

    private Context context;
    private List<Veiculo> listaVeiculos;
    private VeiculoDAO veiculoDAO;

    public VeiculoAdminAdapter(Context context, List<Veiculo> listaVeiculos) {
        this.context = context;
        this.listaVeiculos = listaVeiculos;
        this.veiculoDAO = new VeiculoDAO(context);
    }

    @NonNull
    @Override
    public VeiculoAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_veiculoadm, parent, false);
        return new VeiculoAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VeiculoAdminViewHolder holder, int position) {
        Veiculo veiculo = listaVeiculos.get(position);

        holder.tvModelo.setText(veiculo.getModelo());
        holder.tvAno.setText(String.valueOf(veiculo.getAno()));
        holder.tvPreco.setText(String.format("R$ %.2f", veiculo.getPreco()));

        // Clique em Editar
        holder.ivEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditarVeiculoActivity.class);
            intent.putExtra("veiculo", veiculo);
            context.startActivity(intent);
        });

        holder.ivExcluir.setOnClickListener(v -> {
            boolean sucesso = veiculoDAO.excluirVeiculo(veiculo.getIdVeiculo());
            if (sucesso) {
                listaVeiculos.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, listaVeiculos.size());
                Toast.makeText(context, "Veículo excluído com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Erro ao excluir veículo", Toast.LENGTH_SHORT).show();
            }
        });

        holder.tvDetalhes.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalhesActivity.class);
            intent.putExtra("idVeiculo", veiculo.getIdVeiculo()); // Apenas o ID
            context.startActivity(intent);
        });

        // Clique no item inteiro (opcional)
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Selecionado: " + veiculo.getModelo(), Toast.LENGTH_SHORT).show();
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

    public static class VeiculoAdminViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto, ivFavorito, ivEditar, ivExcluir;
        TextView tvModelo, tvAno, tvPreco, tvDetalhes;

        public VeiculoAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.ivFoto);
            ivFavorito = itemView.findViewById(R.id.ivFavorito);
            ivEditar = itemView.findViewById(R.id.ivEditar);
            ivExcluir = itemView.findViewById(R.id.ivExcluir);
            tvModelo = itemView.findViewById(R.id.tvModelo);
            tvAno = itemView.findViewById(R.id.tvAno);
            tvPreco = itemView.findViewById(R.id.tvPreco);
            tvDetalhes = itemView.findViewById(R.id.tvDetalhes);
        }
    }
}