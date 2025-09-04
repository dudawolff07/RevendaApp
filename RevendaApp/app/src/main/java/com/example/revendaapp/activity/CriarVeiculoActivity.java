package com.example.revendaapp.activity;

import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.revendaapp.R;
import com.example.revendaapp.database.DatabaseHelper;
import com.example.revendaapp.database.VeiculoDAO;
import com.example.revendaapp.model.Veiculo;

import java.util.ArrayList;
import java.util.List;

public class CriarVeiculoActivity extends AppCompatActivity {

    private EditText etModelo, etAno, etPreco;
    private Spinner spMarca, spCor;
    private Button btnCriar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_veiculo);

        etModelo = findViewById(R.id.inputModelo);
        etAno = findViewById(R.id.inputAno);
        etPreco = findViewById(R.id.inputPreco);
        spMarca = findViewById(R.id.spinnerMarca);
        spCor = findViewById(R.id.spinnerCor);
        btnCriar = findViewById(R.id.btnCriar);

        ArrayAdapter<String> adapterMarca = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listarMarcas());
        adapterMarca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(adapterMarca);

        ArrayAdapter<String> adapterCor = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listarCores());
        adapterCor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCor.setAdapter(adapterCor);

        btnCriar.setOnClickListener(v -> criarVeiculo());
    }

    private List<String> listarMarcas() {
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        List<String> lista = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT nome FROM marca", null);
        if (cursor.moveToFirst()) {
            do {
                lista.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    private List<String> listarCores() {
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        List<String> lista = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT nome FROM cor", null);
        if (cursor.moveToFirst()) {
            do {
                lista.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    private void criarVeiculo() {
        String modelo = etModelo.getText().toString().trim();
        String anoStr = etAno.getText().toString().trim();
        String precoStr = etPreco.getText().toString().trim();
        String marca = spMarca.getSelectedItem().toString();
        String cor = spCor.getSelectedItem().toString();

        if (modelo.isEmpty() || anoStr.isEmpty() || precoStr.isEmpty()) {
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
