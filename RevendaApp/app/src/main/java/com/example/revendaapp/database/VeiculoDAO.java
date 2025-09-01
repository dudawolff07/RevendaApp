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

    public int excluirVeiculo(int idVeiculo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("veiculo", "idVeiculo=?", new String[]{String.valueOf(idVeiculo)});
    }
}
