package com.example.revendaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.revendaapp.R;
import com.example.revendaapp.adapter.VeiculoAdapter;
import com.example.revendaapp.database.FavoritoDAO;
import com.example.revendaapp.database.VeiculoDAO;
import com.example.revendaapp.model.Veiculo;

import java.util.List;

public class MainActivity extends AppCompatActivity implements VeiculoAdapter.OnItemClickListener {

    private RecyclerView rvVeiculos;
    private VeiculoAdapter adapter;
    private List<Veiculo> listaVeiculos;
    private VeiculoDAO veiculoDAO;
    private FavoritoDAO favoritoDAO;
    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idUsuario = 1;
        veiculoDAO = new VeiculoDAO(this);
        favoritoDAO = new FavoritoDAO(this);

        inicializarViews();
        carregarVeiculos();
    }

    private void inicializarViews() {
        rvVeiculos = findViewById(R.id.rvVeiculos);
        rvVeiculos.setLayoutManager(new LinearLayoutManager(this));
    }

    private void carregarVeiculos() {
        listaVeiculos = veiculoDAO.listarVeiculos();

        if (listaVeiculos.isEmpty()) {
            Toast.makeText(this, "Nenhum veículo disponível no momento.", Toast.LENGTH_SHORT).show();
        }

        adapter = new VeiculoAdapter(this, listaVeiculos, this);
        rvVeiculos.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_filtrar) {
            abrirFiltros();
            return true;
        } else if (id == R.id.menu_sair) {
            fazerLogout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void abrirFiltros() {
        Toast.makeText(this, "Abrir filtros", Toast.LENGTH_SHORT).show();
    }

    private void fazerLogout() {
        Toast.makeText(this, "Logout realizado", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, com.example.revendaapp.activity.LoginActivity.class));
        finish();
    }

    @Override
    public void onItemClick(Veiculo veiculo) {
        Intent intent = new Intent(this, com.example.revendaapp.activity.DetalhesActivity.class);
        intent.putExtra("veiculo", veiculo);
        startActivity(intent);
    }

    @Override
    public void onFavoritoClick(Veiculo veiculo) {
        boolean isFavorito = favoritoDAO.isFavorito(idUsuario, veiculo.getIdVeiculo());

        if (isFavorito) {
            favoritoDAO.removerFavorito(idUsuario, veiculo.getIdVeiculo());
            Toast.makeText(this, "Removido dos favoritos", Toast.LENGTH_SHORT).show();
        } else {
            favoritoDAO.adicionarFavorito(new com.example.revendaapp.model.Favorito(idUsuario, veiculo.getIdVeiculo()));
            Toast.makeText(this, "Adicionado aos favoritos", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
    }
}