package com.example.revendaapp.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.revendaapp.R;
import com.example.revendaapp.model.Veiculo;

public class DetalhesActivity extends AppCompatActivity {

    private ViewPager2 vpImagens;
    private TextView tvModelo, tvMarcaCorAno, tvPreco;
    private Button btnFavoritar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        inicializarViews();
        carregarDadosVeiculo();
    }

    private void inicializarViews() {
        vpImagens = findViewById(R.id.viewPagerImagens);
        tvModelo = findViewById(R.id.tvModeloDetalhe);
        tvMarcaCorAno = findViewById(R.id.tvMarcaCorAno);
        tvPreco = findViewById(R.id.tvPrecoDetalhe);
        btnFavoritar = findViewById(R.id.btnFavoritarDetalhes);
    }

    private void carregarDadosVeiculo() {
        Veiculo veiculo = (Veiculo) getIntent().getSerializableExtra("veiculo");

        if (veiculo == null) {
            Toast.makeText(this, "Este veículo não está mais disponível", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvModelo.setText(veiculo.getModelo());
        tvMarcaCorAno.setText(veiculo.getMarca() + " • " + veiculo.getCor() + " • " + veiculo.getAno());
        tvPreco.setText(String.format("R$ %.2f", veiculo.getPreco()));

        // TODO: Carregar imagens no ViewPager2
        // TODO: Configurar favorito
    }
}