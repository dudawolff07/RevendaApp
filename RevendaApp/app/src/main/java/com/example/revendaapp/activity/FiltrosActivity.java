package com.example.revendaapp.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.revendaapp.R;
import com.example.revendaapp.database.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;

public class FiltrosActivity extends AppCompatActivity {

    private LinearLayout containerMarcas;
    private LinearLayout containerCores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_filtros);

        containerMarcas = findViewById(R.id.containerMarcas);
        containerCores = findViewById(R.id.containerCores);

        new LoadFiltrosTask().execute();
    }

    private class LoadFiltrosTask extends AsyncTask<Void, Void, FiltrosData> {
        @Override
        protected FiltrosData doInBackground(Void... voids) {
            DatabaseHelper dbHelper = new DatabaseHelper(FiltrosActivity.this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            List<String> marcas = new ArrayList<>();
            List<String> cores = new ArrayList<>();

            Cursor cursorMarca = db.rawQuery("SELECT nome FROM marca", null);
            while (cursorMarca.moveToNext()) {
                marcas.add(cursorMarca.getString(0));
            }
            cursorMarca.close();

            Cursor cursorCor = db.rawQuery("SELECT nome FROM cor", null);
            while (cursorCor.moveToNext()) {
                cores.add(cursorCor.getString(0));
            }
            cursorCor.close();

            db.close();
            return new FiltrosData(marcas, cores);
        }

        @Override
        protected void onPostExecute(FiltrosData filtrosData) {
            preencherMarcas(filtrosData.marcas);
            preencherCores(filtrosData.cores);
        }
    }

    private void preencherMarcas(List<String> marcas) {
        containerMarcas.removeAllViews();
        for (String marca : marcas) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(marca);
            checkBox.setTag(marca);
            checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            checkBox.setPadding(0, 0, 0, 16);
            containerMarcas.addView(checkBox);
        }
    }

    private void preencherCores(List<String> cores) {
        containerCores.removeAllViews();
        for (String cor : cores) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(cor);
            checkBox.setTag(cor);
            checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            checkBox.setPadding(0, 0, 0, 16);
            containerCores.addView(checkBox);
        }
    }

    private static class FiltrosData {
        List<String> marcas;
        List<String> cores;
        FiltrosData(List<String> marcas, List<String> cores) {
            this.marcas = marcas;
            this.cores = cores;
        }
    }
}
