package com.example.revendaapp.activity;
import com.example.revendaapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        // TODO: Implementar funcionalidades do administrador
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sair) {
            fazerLogout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fazerLogout() {
        Toast.makeText(this, "Logout realizado", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, com.example.revendaapp.activity.LoginActivity.class));
        finish();
    }
}