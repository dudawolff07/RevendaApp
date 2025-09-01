package com.example.revendaapp.util;

import android.util.Patterns;

public class Validador {

    public static boolean validarEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean validarSenha(String senha) {
        return senha != null && senha.length() >= 6;
    }

    public static boolean validarConfirmacaoSenha(String senha, String confirmacao) {
        return senha != null && senha.equals(confirmacao);
    }

    public static boolean validarNome(String nome) {
        return nome != null && nome.trim().length() >= 3;
    }

    public static boolean validarPreco(double preco) {
        return preco > 0;
    }

    public static boolean validarAno(int ano) {
        int anoAtual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        return ano >= 1900 && ano <= anoAtual + 1; // Permite até 1 ano no futuro
    }

    public static boolean validarModelo(String modelo) {
        return modelo != null && modelo.trim().length() >= 2;
    }

    public static boolean validarMarca(String marca) {
        return marca != null && !marca.trim().isEmpty();
    }

    public static boolean validarCor(String cor) {
        return cor != null && !cor.trim().isEmpty();
    }
}