package com.example.revendaapp.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.revendaapp.R;
import com.example.revendaapp.database.DatabaseHelper;

public class FiltrosActivity extends AppCompatActivity {

    private GridLayout layoutMarcas, layoutCores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros);

        layoutMarcas = findViewById(R.id.layoutMarcas);
        layoutCores = findViewById(R.id.layoutCores);

        carregarMarcas();
        carregarCores();
    }

    private void carregarMarcas() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT nome FROM marca", null);
        if (cursor.moveToFirst()) {
            do {
                String nomeMarca = cursor.getString(0);

                CheckBox cb = new CheckBox(this);
                cb.setText(nomeMarca);
                cb.setTextColor(ContextCompat.getColor(this, R.color.rev_text_sidebar));

                layoutMarcas.addView(cb);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    private void carregarCores() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT nome FROM cor", null);
        if (cursor.moveToFirst()) {
            do {
                String nomeCor = cursor.getString(0);

                CheckBox cb = new CheckBox(this);
                cb.setText(nomeCor);
                cb.setTextColor(ContextCompat.getColor(this, R.color.rev_text_sidebar));

                layoutCores.addView(cb);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }
}
