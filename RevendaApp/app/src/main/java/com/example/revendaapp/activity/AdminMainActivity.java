package com.example.revendaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.revendaapp.R;
import com.example.revendaapp.adapter.VeiculoAdminAdapter;
import com.example.revendaapp.database.VeiculoDAO;
import com.example.revendaapp.model.Veiculo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AdminMainActivity extends AppCompatActivity {

    private RecyclerView rvVeiculosAdmin;
    private VeiculoAdminAdapter veiculoAdminAdapter;
    private VeiculoDAO veiculoDAO;

    private final ActivityResultLauncher<Intent> criarVeiculoLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> carregarVeiculos());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        veiculoDAO = new VeiculoDAO(this);

        // RecyclerView
        rvVeiculosAdmin = findViewById(R.id.rvVeiculosAdmin);
        rvVeiculosAdmin.setLayoutManager(new GridLayoutManager(this, 2));

        // Adapter específico para admin
        veiculoAdminAdapter = new VeiculoAdminAdapter(this, veiculoDAO.listarVeiculos());
        rvVeiculosAdmin.setAdapter(veiculoAdminAdapter);

        FloatingActionButton fabAdicionar = findViewById(R.id.fabAdicionar);
        fabAdicionar.setOnClickListener(v -> {
            Intent intent = new Intent(this, CriarVeiculoActivity.class);
            criarVeiculoLauncher.launch(intent);
        });

        // Sidebar de filtros
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        Button btnFiltrar = findViewById(R.id.btnFiltrar);

        btnFiltrar.setOnClickListener(v -> {
            carregarFiltrosSidebar();
            drawerLayout.openDrawer(Gravity.END);
        });

        carregarVeiculos();
    }

    private void carregarVeiculos() {
        List<Veiculo> lista = veiculoDAO.listarVeiculos();
        veiculoAdminAdapter.atualizarLista(lista);
    }

    private void carregarFiltrosSidebar() {
        LinearLayout containerMarcas = findViewById(R.id.containerMarcas);
        LinearLayout containerCores = findViewById(R.id.containerCores);

        containerMarcas.removeAllViews();
        containerCores.removeAllViews();

        List<String> marcas = veiculoDAO.listarMarcas();
        List<String> cores = veiculoDAO.listarCores();

        for (String marca : marcas) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(marca);
            containerMarcas.addView(checkBox);
        }

        for (String cor : cores) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(cor);
            containerCores.addView(checkBox);
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        carregarVeiculos();
    }
}