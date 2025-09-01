package com.example.revendaapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.revendaapp.model.Favorito;
import com.example.revendaapp.model.Veiculo;

import java.util.ArrayList;
import java.util.List;

public class FavoritoDAO {
    private DatabaseHelper dbHelper;

    public FavoritoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long adicionarFavorito(Favorito favorito) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idUsuario", favorito.getIdUsuario());
        valores.put("idVeiculo", favorito.getIdVeiculo());
        return db.insert("favorito", null, valores);
    }

    public int removerFavorito(int idUsuario, int idVeiculo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("favorito", "idUsuario=? AND idVeiculo=?",
                new String[]{String.valueOf(idUsuario), String.valueOf(idVeiculo)});
    }

    public boolean isFavorito(int idUsuario, int idVeiculo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM favorito WHERE idUsuario=? AND idVeiculo=?",
                new String[]{String.valueOf(idUsuario), String.valueOf(idVeiculo)});

        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    public List<Veiculo> listarFavoritos(int idUsuario) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Veiculo> lista = new ArrayList<>();

        String query = "SELECT v.* FROM veiculo v " +
                "INNER JOIN favorito f ON v.idVeiculo = f.idVeiculo " +
                "WHERE f.idUsuario = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUsuario)});

        if (cursor.moveToFirst()) {
            do {
                Veiculo v = new Veiculo(
                        cursor.getInt(cursor.getColumnIndexOrThrow("idVeiculo")),
                        cursor.getString(cursor.getColumnIndexOrThrow("marca")),
                        cursor.getString(cursor.getColumnIndexOrThrow("modelo")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("ano")),
                        cursor.getString(cursor.getColumnIndexOrThrow("cor")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("preco")),
                        cursor.getString(cursor.getColumnIndexOrThrow("fotoCapa"))
                );
                lista.add(v);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }
}