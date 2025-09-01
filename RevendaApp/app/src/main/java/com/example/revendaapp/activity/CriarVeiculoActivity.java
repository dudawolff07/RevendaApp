package com.example.revendaapp.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.revendaapp.R;
import com.example.revendaapp.database.VeiculoDAO;
import com.example.revendaapp.model.Veiculo;

public class CriarVeiculoActivity extends AppCompatActivity {

    private EditText etModelo, etMarca, etCor, etAno, etPreco;
    private Button btnCriar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_veiculo);

        // Corrigindo IDs para corresponder ao layout
        etModelo = findViewById(R.id.inputModelo);
        etMarca = findViewById(R.id.inputMarca);
        etCor = findViewById(R.id.inputCor);
        etAno = findViewById(R.id.inputAno);
        etPreco = findViewById(R.id.inputPreco);
        btnCriar = findViewById(R.id.btnCriar);

        btnCriar.setOnClickListener(v -> criarVeiculo());
    }

    private void criarVeiculo() {
        String modelo = etModelo.getText().toString().trim();
        String marca = etMarca.getText().toString().trim();
        String cor = etCor.getText().toString().trim();
        String anoStr = etAno.getText().toString().trim();
        String precoStr = etPreco.getText().toString().trim();

        if (modelo.isEmpty() || marca.isEmpty() || cor.isEmpty() || anoStr.isEmpty() || precoStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int ano;
        double preco;

        try {
            ano = Integer.parseInt(anoStr);
            preco = Double.parseDouble(precoStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ano ou preço inválidos", Toast.LENGTH_SHORT).show();
            return;
        }

        Veiculo veiculo = new Veiculo(0, marca, modelo, ano, cor, preco, "");
        VeiculoDAO veiculoDAO = new VeiculoDAO(this);

        long resultado = veiculoDAO.inserirVeiculo(veiculo);

        if (resultado != -1) {
            Toast.makeText(this, "Veículo cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao cadastrar veículo", Toast.LENGTH_SHORT).show();
        }
    }
}