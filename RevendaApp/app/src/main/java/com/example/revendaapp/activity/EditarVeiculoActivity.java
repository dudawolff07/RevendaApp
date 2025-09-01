package com.example.revendaapp.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.revendaapp.R;
import com.example.revendaapp.database.VeiculoDAO;
import com.example.revendaapp.model.Veiculo;

public class EditarVeiculoActivity extends AppCompatActivity {

    private EditText etModelo, etMarca, etCor, etAno, etPreco;
    private Button btnEditar;
    private Veiculo veiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_veiculo);

        veiculo = (Veiculo) getIntent().getSerializableExtra("veiculo");

        if (veiculo == null) {
            Toast.makeText(this, "Veículo não encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Corrigindo IDs para corresponder ao layout
        etModelo = findViewById(R.id.inputModelo);
        etMarca = findViewById(R.id.inputMarca);
        etCor = findViewById(R.id.inputCor);
        etAno = findViewById(R.id.inputAno);
        etPreco = findViewById(R.id.inputPreco);
        btnEditar = findViewById(R.id.btnSalvar); // ID correto: btnSalvar

        preencherCampos();
        btnEditar.setOnClickListener(v -> editarVeiculo());
    }

    private void preencherCampos() {
        etModelo.setText(veiculo.getModelo());
        etMarca.setText(veiculo.getMarca());
        etCor.setText(veiculo.getCor());
        etAno.setText(String.valueOf(veiculo.getAno()));
        etPreco.setText(String.valueOf(veiculo.getPreco()));
    }

    private void editarVeiculo() {
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

        veiculo.setModelo(modelo);
        veiculo.setMarca(marca);
        veiculo.setCor(cor);
        veiculo.setAno(ano);
        veiculo.setPreco(preco);

        VeiculoDAO veiculoDAO = new VeiculoDAO(this);
        int resultado = veiculoDAO.atualizarVeiculo(veiculo);

        if (resultado > 0) {
            Toast.makeText(this, "Veículo atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao atualizar veículo", Toast.LENGTH_SHORT).show();
        }
    }
}