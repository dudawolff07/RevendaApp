package com.example.revendaapp.activity;

import com.example.revendaapp.R;
import com.example.revendaapp.adapter.VeiculoAdapter;
import com.example.revendaapp.database.VeiculoDAO;
import com.example.revendaapp.model.Veiculo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AdminMainActivity extends AppCompatActivity {

    private RecyclerView rvVeiculosAdmin;
    private VeiculoAdapter veiculoAdapter;
    private VeiculoDAO veiculoDAO;

    private final ActivityResultLauncher<Intent> criarVeiculoLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> carregarVeiculos());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        veiculoDAO = new VeiculoDAO(this);

        // RecyclerView
        rvVeiculosAdmin = findViewById(R.id.rvVeiculosAdmin);
        rvVeiculosAdmin.setLayoutManager(new LinearLayoutManager(this));

        // Adapter
        veiculoAdapter = new VeiculoAdapter(this, veiculoDAO.listarVeiculos(), new VeiculoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Veiculo veiculo) {
                Toast.makeText(AdminMainActivity.this, "Selecionado: " + veiculo.getModelo(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFavoritoClick(Veiculo veiculo) {
                Toast.makeText(AdminMainActivity.this, "Favorito: " + veiculo.getModelo(), Toast.LENGTH_SHORT).show();

            }
        });
        rvVeiculosAdmin.setAdapter(veiculoAdapter);

        FloatingActionButton fabAdicionar = findViewById(R.id.fabAdicionar);
        fabAdicionar.setOnClickListener(v -> {
            Intent intent = new Intent(this, CriarVeiculoActivity.class);
            criarVeiculoLauncher.launch(intent);
        });

        carregarVeiculos();
    }

    private void carregarVeiculos() {
        List<Veiculo> lista = veiculoDAO.listarVeiculos();
        veiculoAdapter.atualizarLista(lista);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sair) {
            fazerLogout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fazerLogout() {
        Toast.makeText(this, "Logout realizado", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
