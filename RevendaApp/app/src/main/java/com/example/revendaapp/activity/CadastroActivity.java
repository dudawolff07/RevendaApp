package com.example.revendaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.revendaapp.R;
import com.example.revendaapp.database.UsuarioDAO;
import com.example.revendaapp.model.Usuario;
import com.example.revendaapp.util.Validador;

public class CadastroActivity extends AppCompatActivity {

    private EditText etNome, etEmail, etSenha, etConfirmarSenha;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Corrigindo os IDs para corresponder ao layout
        etNome = findViewById(R.id.inputNome);           // ID correto: inputNome
        etEmail = findViewById(R.id.inputEmail);         // ID correto: inputEmail
        etSenha = findViewById(R.id.inputSenha);         // ID correto: inputSenha
        etConfirmarSenha = findViewById(R.id.inputConfirmar);  // ID correto: inputConfirmar
        btnCadastrar = findViewById(R.id.btnCriarCadastro);    // ID correto: btnCriarCadastro

        btnCadastrar.setOnClickListener(v -> criarCadastro());
    }

    private void criarCadastro() {
        String nome = etNome.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();
        String confirmarSenha = etConfirmarSenha.getText().toString().trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Validador.validarNome(nome)) {
            Toast.makeText(this, "Nome deve ter pelo menos 3 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Validador.validarEmail(email)) {
            Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Validador.validarSenha(senha)) {
            Toast.makeText(this, "Senha deve ter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Validador.validarConfirmacaoSenha(senha, confirmarSenha)) {
            Toast.makeText(this, "As senhas não correspondem", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = new Usuario(0, nome, email, senha, "Cliente");
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);

        long resultado = usuarioDAO.inserirUsuario(usuario);

        if (resultado != -1) {
            Toast.makeText(this, "Cadastro concluído! Faça login.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, com.example.revendaapp.activity.LoginActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Este e-mail já está em uso. Use outro.", Toast.LENGTH_SHORT).show();
        }
    }
}