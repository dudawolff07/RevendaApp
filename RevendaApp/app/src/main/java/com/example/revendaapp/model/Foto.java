package com.example.revendaapp.model;

public class Foto {
    private int idFoto;
    private byte[] selecionarFoto;
    private int idVeiculo;

    public Foto() {}

    public Foto(int idFoto, byte[] selecionarFoto, int idVeiculo) {
        this.idFoto = idFoto;
        this.selecionarFoto = selecionarFoto;
        this.idVeiculo = idVeiculo;
    }

    public int getIdFoto() { return idFoto; }
    public void setIdFoto(int idFoto) { this.idFoto = idFoto; }

    public byte[] getSelecionarFoto() { return selecionarFoto; }
    public void setSelecionarFoto(byte[] selecionarFoto) { this.selecionarFoto = selecionarFoto; }

    public int getIdVeiculo() { return idVeiculo; }
    public void setIdVeiculo(int idVeiculo) { this.idVeiculo = idVeiculo; }
}
