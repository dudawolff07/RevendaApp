package com.example.revendaapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.revendaapp.R;
import com.example.revendaapp.database.DatabaseHelper;
import com.example.revendaapp.database.VeiculoDAO;
import com.example.revendaapp.model.Veiculo;

import java.util.ArrayList;
import java.util.List;

public class CriarVeiculoActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_CARD_REQUEST = 1;
    private static final int PICK_IMAGES_CARROSSEL_REQUEST = 2;

    private EditText etModelo, etAno, etPreco;
    private Spinner spMarca, spCor;
    private Button btnCriar, btnAddImagemCard, btnSelecionarImagens;
    private Uri imagemCardUri;
    private ArrayList<Uri> imagensCarrosselUris = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_veiculo);

        inicializarComponentes();

        configurarSpinners();

        configurarListeners();
    }

    private void inicializarComponentes() {
        etModelo = findViewById(R.id.inputModelo);
        etAno = findViewById(R.id.inputAno);
        etPreco = findViewById(R.id.inputPreco);
        spMarca = findViewById(R.id.spinnerMarca);
        spCor = findViewById(R.id.spinnerCor);
        btnCriar = findViewById(R.id.btnCriar);
        btnAddImagemCard = findViewById(R.id.btnAddImagemCard);
        btnSelecionarImagens = findViewById(R.id.btnSelecionarImagens);
    }

    private void configurarSpinners() {
        ArrayAdapter<String> adapterMarca = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listarMarcas());
        adapterMarca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMarca.setAdapter(adapterMarca);

        ArrayAdapter<String> adapterCor = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listarCores());
        adapterCor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCor.setAdapter(adapterCor);
    }

    private void configurarListeners() {
        btnAddImagemCard.setOnClickListener(v -> abrirGaleria(PICK_IMAGE_CARD_REQUEST, false));
        btnSelecionarImagens.setOnClickListener(v -> abrirGaleria(PICK_IMAGES_CARROSSEL_REQUEST, true));
        btnCriar.setOnClickListener(v -> criarVeiculo());
    }

    private void abrirGaleria(int requestCode, boolean permitirMultiplas) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (permitirMultiplas) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }

        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PICK_IMAGE_CARD_REQUEST) {
                // Imagem única para o card
                imagemCardUri = data.getData();
                Toast.makeText(this, "Imagem do card selecionada", Toast.LENGTH_SHORT).show();

            } else if (requestCode == PICK_IMAGES_CARROSSEL_REQUEST) {
                // Múltiplas imagens para o carrossel
                imagensCarrosselUris.clear(); // Limpar seleções anteriores

                if (data.getClipData() != null) {
                    // Múltiplas imagens selecionadas
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        imagensCarrosselUris.add(imageUri);
                    }
                    Toast.makeText(this, count + " imagens selecionadas para o carrossel", Toast.LENGTH_SHORT).show();
                } else if (data.getData() != null) {
                    // Apenas uma imagem selecionada
                    Uri imageUri = data.getData();
                    imagensCarrosselUris.add(imageUri);
                    Toast.makeText(this, "1 imagem selecionada para o carrossel", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private List<String> listarMarcas() {
        List<String> lista = new ArrayList<>();
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nome FROM marca", null);

        if (cursor.moveToFirst()) {
            do {
                lista.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return lista;
    }

    private List<String> listarCores() {
        List<String> lista = new ArrayList<>();
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nome FROM cor", null);

        if (cursor.moveToFirst()) {
            do {
                lista.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return lista;
    }

    private void criarVeiculo() {
        String modelo = etModelo.getText().toString().trim();
        String anoStr = etAno.getText().toString().trim();
        String precoStr = etPreco.getText().toString().trim();
        String marca = spMarca.getSelectedItem().toString();
        String cor = spCor.getSelectedItem().toString();

        if (!validarCampos(modelo, anoStr, precoStr)) {
            return;
        }

        if (!validarImagens()) {
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

        String caminhoImagemCard = salvarImagem(imagemCardUri, "card_");
        String caminhosCarrossel = processarImagensCarrossel();

        Veiculo veiculo = new Veiculo(0, marca, modelo, ano, cor, preco, caminhoImagemCard, caminhosCarrossel);

        salvarVeiculo(veiculo);
    }

    private boolean validarCampos(String modelo, String anoStr, String precoStr) {
        if (modelo.isEmpty() || anoStr.isEmpty() || precoStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validarImagens() {
        if (imagemCardUri == null) {
            Toast.makeText(this, "Selecione uma imagem para o card", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (imagensCarrosselUris.isEmpty()) {
            Toast.makeText(this, "Selecione pelo menos uma imagem para o carrossel", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private String salvarImagem(Uri imageUri, String prefixo) {
        return imageUri.toString();
    }

    private String processarImagensCarrossel() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < imagensCarrosselUris.size(); i++) {
            String caminho = salvarImagem(imagensCarrosselUris.get(i), "carrossel_" + i + "_");
            if (sb.length() > 0) {
                sb.append(";");
            }
            sb.append(caminho);
        }
        return sb.toString();
    }

    private void salvarVeiculo(Veiculo veiculo) {
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