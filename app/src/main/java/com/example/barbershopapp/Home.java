package com.example.barbershopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Home extends AppCompatActivity {
    private TextView txtNombreUsuario;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);

        String nombre = getIntent().getStringExtra("nome");

        txtNombreUsuario.setText("Bienvenido(a), " + nombre);
    }
}