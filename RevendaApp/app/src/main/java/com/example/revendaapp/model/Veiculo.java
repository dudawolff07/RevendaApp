package com.example.revendaapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Veiculo implements Parcelable {
    private int idVeiculo;
    private String marca;
    private String modelo;
    private int ano;
    private String cor;
    private double preco;
    private String fotoCapa;
    private String fotosCarrossel; // Novo campo para múltiplas imagens

    // Construtor completo
    public Veiculo(int idVeiculo, String marca, String modelo, int ano, String cor,
                   double preco, String fotoCapa, String fotosCarrossel) {
        this.idVeiculo = idVeiculo;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.preco = preco;
        this.fotoCapa = fotoCapa;
        this.fotosCarrossel = fotosCarrossel;
    }

    // Construtor para compatibilidade (sem carrossel)
    public Veiculo(int idVeiculo, String marca, String modelo, int ano, String cor,
                   double preco, String fotoCapa) {
        this(idVeiculo, marca, modelo, ano, cor, preco, fotoCapa, "");
    }

    protected Veiculo(Parcel in) {
        idVeiculo = in.readInt();
        marca = in.readString();
        modelo = in.readString();
        ano = in.readInt();
        cor = in.readString();
        preco = in.readDouble();
        fotoCapa = in.readString();
        fotosCarrossel = in.readString();
    }

    public static final Creator<Veiculo> CREATOR = new Creator<Veiculo>() {
        @Override
        public Veiculo createFromParcel(Parcel in) {
            return new Veiculo(in);
        }

        @Override
        public Veiculo[] newArray(int size) {
            return new Veiculo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idVeiculo);
        dest.writeString(marca);
        dest.writeString(modelo);
        dest.writeInt(ano);
        dest.writeString(cor);
        dest.writeDouble(preco);
        dest.writeString(fotoCapa);
        dest.writeString(fotosCarrossel);
    }

    // Getters e Setters
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

    public String getFotosCarrossel() { return fotosCarrossel; }
    public void setFotosCarrossel(String fotosCarrossel) { this.fotosCarrossel = fotosCarrossel; }

    // Método utilitário para adicionar imagens ao carrossel
    public void adicionarImagemCarrossel(String imagemUrl) {
        if (fotosCarrossel == null || fotosCarrossel.isEmpty()) {
            fotosCarrossel = imagemUrl;
        } else {
            fotosCarrossel += ";" + imagemUrl;
        }
    }

    public List<String> getListaFotosCarrossel() {
        List<String> lista = new ArrayList<>();
        if (fotosCarrossel != null && !fotosCarrossel.isEmpty()) {
            String[] imagens = fotosCarrossel.split(";");
            for (String imagem : imagens) {
                if (!imagem.trim().isEmpty()) {
                    lista.add(imagem.trim());
                }
            }
        }
        return lista;
    }

    @Override
    public String toString() {
        return "Veiculo{" +
                "idVeiculo=" + idVeiculo +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                ", cor='" + cor + '\'' +
                ", preco=" + preco +
                ", fotoCapa='" + fotoCapa + '\'' +
                ", fotosCarrossel='" + fotosCarrossel + '\'' +
                '}';
    }
}