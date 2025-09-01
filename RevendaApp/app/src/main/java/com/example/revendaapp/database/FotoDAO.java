package com.example.revendaapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.revendaapp.model.Foto;

import java.util.ArrayList;
import java.util.List;

public class FotoDAO {
    private DatabaseHelper dbHelper;

    public FotoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long inserirFoto(Foto foto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("SelecionarFoto", foto.getSelecionarFoto());
        valores.put("FK_idVeiculo", foto.getIdVeiculo());
        return db.insert("fotos", null, valores);
    }

    public List<Foto> listarFotosPorVeiculo(int idVeiculo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Foto> lista = new ArrayList<>();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM fotos WHERE FK_idVeiculo = ?",
                new String[]{String.valueOf(idVeiculo)});

        if (cursor.moveToFirst()) {
            do {
                Foto foto = new Foto(
                        cursor.getInt(cursor.getColumnIndexOrThrow("idFoto")),
                        cursor.getBlob(cursor.getColumnIndexOrThrow("SelecionarFoto")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("FK_idVeiculo"))
                );
                lista.add(foto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    public int excluirFotosPorVeiculo(int idVeiculo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("fotos", "FK_idVeiculo=?", new String[]{String.valueOf(idVeiculo)});
    }

    public int excluirFoto(int idFoto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("fotos", "idFoto=?", new String[]{String.valueOf(idFoto)});
    }
}