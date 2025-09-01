package com.example.revendaapp.model;

public class Favorito {
    private int idUsuario;
    private int idVeiculo;

    public Favorito() {}

    public Favorito(int idUsuario, int idVeiculo) {
        this.idUsuario = idUsuario;
        this.idVeiculo = idVeiculo;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdVeiculo() { return idVeiculo; }
    public void setIdVeiculo(int idVeiculo) { this.idVeiculo = idVeiculo; }
}
