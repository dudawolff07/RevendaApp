package com.example.revendaapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.revendaapp.R;
import com.example.revendaapp.database.VeiculoDAO;
import com.example.revendaapp.model.Veiculo;

public class DetalhesActivity extends AppCompatActivity {

    private ViewPager2 vpImagens;
    private ImageView btnPrevious, btnNext;
    private TextView tvModelo, tvAno, tvMarcaCor, tvPreco;
    private VeiculoDAO veiculoDAO;
    private Veiculo veiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        veiculoDAO = new VeiculoDAO(this);
        inicializarViews();
        configurarNavegacaoImagens();
        carregarDadosVeiculo();
    }

    private void inicializarViews() {
        vpImagens = findViewById(R.id.viewPagerImagens);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        tvModelo = findViewById(R.id.tvModeloDetalhe);
        tvAno = findViewById(R.id.tvAno);
        tvMarcaCor = findViewById(R.id.tvMarcaCor);
        tvPreco = findViewById(R.id.tvPrecoDetalhe);
    }

    private void configurarNavegacaoImagens() {
        btnPrevious.setOnClickListener(v -> {
            int currentItem = vpImagens.getCurrentItem();
            if (currentItem > 0) {
                vpImagens.setCurrentItem(currentItem - 1);
            }
        });

        btnNext.setOnClickListener(v -> {
            int currentItem = vpImagens.getCurrentItem();
            if (vpImagens.getAdapter() != null &&
                    currentItem < vpImagens.getAdapter().getItemCount() - 1) {
                vpImagens.setCurrentItem(currentItem + 1);
            }
        });

        vpImagens.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if (position == 0) {
                    btnPrevious.setAlpha(0.5f);
                    btnPrevious.setEnabled(false);
                } else {
                    btnPrevious.setAlpha(1.0f);
                    btnPrevious.setEnabled(true);
                }

                if (vpImagens.getAdapter() != null &&
                        position == vpImagens.getAdapter().getItemCount() - 1) {
                    btnNext.setAlpha(0.5f);
                    btnNext.setEnabled(false);
                } else {
                    btnNext.setAlpha(1.0f);
                    btnNext.setEnabled(true);
                }
            }
        });
    }

    private void carregarDadosVeiculo() {
        // Buscar o ID do veículo em vez do objeto completo
        int idVeiculo = getIntent().getIntExtra("idVeiculo", -1);

        Log.d("DetalhesActivity", "ID recebido: " + idVeiculo);

        if (idVeiculo == -1) {
            Toast.makeText(this, "Veículo não disponível", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Buscar veículo no banco de dados
        veiculo = veiculoDAO.buscarVeiculoPorId(idVeiculo);

        if (veiculo == null) {
            Toast.makeText(this, "Veículo não encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Preencher os dados na interface
        tvModelo.setText(veiculo.getModelo());
        tvAno.setText(String.valueOf(veiculo.getAno())); // Convert int to String
        tvMarcaCor.setText(veiculo.getMarca() + " • " + veiculo.getCor());
        tvPreco.setText(String.format("R$ %.2f", veiculo.getPreco()));

        // Aqui você pode configurar o ViewPager com as imagens do veículo
        // Se tiver um adapter para as imagens, configure aqui:
        // if (veiculo.getFotoCapa() != null) {
        //     configurarImagens(veiculo.getFotoCapa());
        // }
    }

    // Método para configurar as imagens no ViewPager (exemplo)
    private void configurarImagens(String fotoCapa) {
        // Se você tiver um adapter para o ViewPager, configure aqui
        // List<String> imagens = Arrays.asList(fotoCapa, "outra_imagem.jpg");
        // vpImagens.setAdapter(new ImagemAdapter(this, imagens));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (veiculoDAO != null) {
            veiculoDAO.close();
        }
    }
}