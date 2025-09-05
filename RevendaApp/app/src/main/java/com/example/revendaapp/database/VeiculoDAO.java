package com.example.revendaapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.revendaapp.model.Veiculo;

import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {
    private DatabaseHelper dbHelper;

    public VeiculoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long inserirVeiculo(Veiculo v) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("marca", v.getMarca());
        valores.put("modelo", v.getModelo());
        valores.put("ano", v.getAno());
        valores.put("cor", v.getCor());
        valores.put("preco", v.getPreco());
        valores.put("fotoCapa", v.getFotoCapa());
        valores.put("fotos_carrossel", v.getFotosCarrossel());
        return db.insert("veiculo", null, valores);
    }

    public List<Veiculo> listarVeiculos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Veiculo> lista = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM veiculo", null);
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

    public int atualizarVeiculo(Veiculo v) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("marca", v.getMarca());
        valores.put("modelo", v.getModelo());
        valores.put("ano", v.getAno());
        valores.put("cor", v.getCor());
        valores.put("preco", v.getPreco());
        valores.put("fotoCapa", v.getFotoCapa());
        return db.update("veiculo", valores, "idVeiculo=?", new String[]{String.valueOf(v.getIdVeiculo())});
    }

    public boolean excluirVeiculo(int idVeiculo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int linhasAfetadas = db.delete("veiculo", "idVeiculo=?", new String[]{String.valueOf(idVeiculo)});
        return linhasAfetadas > 0;
    }


    public List<String> listarMarcas() {
        List<String> marcas = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(true,
                "veiculo", // nome correto da tabela
                new String[]{"marca"},
                null, null, "marca", null, "marca ASC", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String marca = cursor.getString(cursor.getColumnIndexOrThrow("marca"));
                if (marca != null && !marca.trim().isEmpty()) {
                    marcas.add(marca);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return marcas;
    }

    public List<String> listarCores() {
        List<String> cores = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(true,
                "veiculos",
                new String[]{"cor"},
                null, null, "cor", null, "cor ASC", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String cor = cursor.getString(cursor.getColumnIndexOrThrow("cor"));
                if (cor != null && !cor.trim().isEmpty()) {
                    cores.add(cor);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return cores;
    }

    public Veiculo buscarVeiculoPorId(int idVeiculo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Veiculo veiculo = null;

        Cursor cursor = db.query("veiculo",
                null,
                "idVeiculo = ?",
                new String[]{String.valueOf(idVeiculo)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            veiculo = new Veiculo(
                    cursor.getInt(cursor.getColumnIndexOrThrow("idVeiculo")),
                    cursor.getString(cursor.getColumnIndexOrThrow("marca")),
                    cursor.getString(cursor.getColumnIndexOrThrow("modelo")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("ano")),
                    cursor.getString(cursor.getColumnIndexOrThrow("cor")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("preco")),
                    cursor.getString(cursor.getColumnIndexOrThrow("fotoCapa"))
            );
            cursor.close();
        }

        db.close();
        return veiculo;
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
