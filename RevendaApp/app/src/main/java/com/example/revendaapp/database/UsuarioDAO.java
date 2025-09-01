package com.example.revendaapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.revendaapp.model.Usuario;

public class UsuarioDAO {
    private DatabaseHelper dbHelper;

    public UsuarioDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long inserirUsuario(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getNome());
        valores.put("email", usuario.getEmail());
        valores.put("senha", usuario.getSenha());
        valores.put("tipo", usuario.getTipo());
        return db.insert("usuario", null, valores);
    }

    public Usuario login(String email, String senha) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM usuario WHERE email = ? AND senha = ?",
                new String[]{email, senha});
        if (cursor.moveToFirst()) {
            Usuario usuario = new Usuario(
                    cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("senha")),
                    cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
            );
            cursor.close();
            return usuario;
        }
        cursor.close();
        return null;
    }
}
