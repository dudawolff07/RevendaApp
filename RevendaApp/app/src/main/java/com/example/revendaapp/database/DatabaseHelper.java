package com.example.revendaapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sistema_veiculos.db";
    private static final int DATABASE_VERSION = 2; // IMPORTANTE: subir versão para recriar as tabelas

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuario (" +
                "idUsuario INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "email TEXT NOT NULL UNIQUE," +
                "senha TEXT NOT NULL," +
                "tipo TEXT NOT NULL" +
                ");");

        db.execSQL("CREATE TABLE marca (" +
                "idMarca INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL UNIQUE" +
                ");");

        db.execSQL("CREATE TABLE cor (" +
                "idCor INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL UNIQUE" +
                ");");

        db.execSQL("CREATE TABLE veiculo (" +
                "idVeiculo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "marca TEXT NOT NULL," +   // se quiser pode trocar para FK_idMarca
                "modelo TEXT NOT NULL," +
                "ano INTEGER," +
                "cor TEXT NOT NULL," +     // idem: poderia ser FK_idCor
                "preco REAL," +
                "fotoCapa TEXT" +
                ");");

        db.execSQL("CREATE TABLE fotos (" +
                "idFoto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "SelecionarFoto BLOB NOT NULL," +
                "FK_idVeiculo INTEGER NOT NULL," +
                "FOREIGN KEY(FK_idVeiculo) REFERENCES veiculo(idVeiculo) ON DELETE CASCADE" +
                ");");

        db.execSQL("CREATE TABLE favorito (" +
                "idUsuario INTEGER NOT NULL," +
                "idVeiculo INTEGER NOT NULL," +
                "PRIMARY KEY(idUsuario, idVeiculo)," +
                "FOREIGN KEY(idUsuario) REFERENCES usuario(idUsuario) ON DELETE CASCADE," +
                "FOREIGN KEY(idVeiculo) REFERENCES veiculo(idVeiculo) ON DELETE CASCADE" +
                ");");

        // Inserindo admin padrão
        db.execSQL("INSERT INTO usuario (nome, email, senha, tipo) " +
                "VALUES ('Admin Padrão', 'admin@sistema.com', 'admin123', 'Administrador');");

        // Inserindo marcas
        db.execSQL("INSERT INTO marca (nome) VALUES " +
                "('Chevrolet')," +
                "('Volkswagen')," +
                "('Renault')," +
                "('Fiat')," +
                "('Hyundai')," +
                "('Peugeot')," +
                "('Citroën');");

        // Inserindo cores
        db.execSQL("INSERT INTO cor (nome) VALUES " +
                "('Cinza')," +
                "('Vermelho')," +
                "('Branco')," +
                "('Prata')," +
                "('Azul')," +
                "('Preto');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS favorito");
        db.execSQL("DROP TABLE IF EXISTS fotos");
        db.execSQL("DROP TABLE IF EXISTS veiculo");
        db.execSQL("DROP TABLE IF EXISTS cor");
        db.execSQL("DROP TABLE IF EXISTS marca");
        db.execSQL("DROP TABLE IF EXISTS usuario");
        onCreate(db);
    }
}
