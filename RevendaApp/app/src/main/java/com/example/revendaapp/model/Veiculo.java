package com.example.revendaapp.model;

public class Veiculo {
    private int idVeiculo;
    private String marca;
    private String modelo;
    private int ano;
    private String cor;
    private double preco;
    private String fotoCapa;

    public Veiculo() {}

    public Veiculo(int idVeiculo, String marca, String modelo, int ano, String cor, double preco, String fotoCapa) {
        this.idVeiculo = idVeiculo;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.preco = preco;
        this.fotoCapa = fotoCapa;
    }

    public int getIdVeiculo() { return idVeiculo; }
    public void setIdVeiculo(int idVeiculo) { this.idVeiculo = idVeiculo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public String getFotoCapa() { return fotoCapa; }
    public void setFotoCapa(String fotoCapa) { this.fotoCapa = fotoCapa; }
}
