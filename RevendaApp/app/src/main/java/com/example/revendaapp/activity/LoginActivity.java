package com.example.revendaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.revendaapp.R;
import com.example.revendaapp.database.UsuarioDAO;
import com.example.revendaapp.model.Usuario;
import com.example.revendaapp.util.Validador;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etSenha;
    private Button btnLogin;
    private TextView tvCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Corrigindo os IDs para corresponder ao layout
        etEmail = findViewById(R.id.inputEmail);  // ID correto: inputEmail
        etSenha = findViewById(R.id.inputSenha);  // ID correto: inputSenha
        btnLogin = findViewById(R.id.btnLogin);   // ID correto: btnLogin
        tvCadastro = findViewById(R.id.linkCadastro);  // ID correto: linkCadastro

        btnLogin.setOnClickListener(v -> fazerLogin());
        tvCadastro.setOnClickListener(v -> abrirCadastro());
    }

    private void fazerLogin() {
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Validador.validarEmail(email)) {
            Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO(this);
        Usuario usuario = usuarioDAO.login(email, senha);

        if (usuario != null) {
            Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();

            if (usuario.getTipo().equals("Administrador")) {
                startActivity(new Intent(this, com.example.revendaapp.activity.AdminMainActivity.class));
            } else {
                startActivity(new Intent(this, com.example.revendaapp.activity.MainActivity.class));
            }
            finish();
        } else {
            Toast.makeText(this, "Email ou senha incorretos. Tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirCadastro() {
        startActivity(new Intent(this, com.example.revendaapp.activity.CadastroActivity.class));
    }
}