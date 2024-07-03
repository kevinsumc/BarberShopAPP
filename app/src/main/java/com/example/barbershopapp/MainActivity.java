package com.example.barbershopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private EditText nombreUser, emailUser, contrasenaUser, reiterarContraseña;

    private Button btnRegistrarse, btnSalir;
    private DataBase dbHelper;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos los elementos del layout
        nombreUser = findViewById(R.id.nombreUser);
        emailUser = findViewById(R.id.emailUser);
        contrasenaUser = findViewById(R.id.contrasenaUser);
        reiterarContraseña = findViewById(R.id.reiterarContra);
        btnRegistrarse = findViewById(R.id.btnRegistrar);
        btnSalir = findViewById(R.id.btnSalir);

        // Inicializamos el helper de la base de datos
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad registro
                Intent intent = new Intent(MainActivity.this, registro.class);
                startActivity(intent);
                registrarUsuario();
            }
        });
    }
    private void registrarUsuario(){
        //Obtenemos los valores ingresados por el usuario
        String nombre = nombreUser.getText().toString().trim();
        String email = emailUser.getText().toString().trim();
        String contraseña = contrasenaUser.getText().toString().trim();
        String reiterarContra = reiterarContraseña.getText().toString().trim();

        dbHelper = new DataBase(this);

        //verificamos que los campos esten llenos
        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(email) || TextUtils.isEmpty(contraseña) || TextUtils.isEmpty(reiterarContra)){
            Toast.makeText(this,"Todos los campos son obligatorios!",Toast.LENGTH_SHORT).show();
            return;

        }
        //verificamos que las contraseñas tengan al menos 6 caracteres
        if (contraseña.length()<6){
            Toast.makeText(this,"La contraseña debe tener al menos 6 caracteres!",Toast.LENGTH_SHORT).show();
            return;

        }
        //verificamos que las contraseñas coincidan
        if (!contraseña.equals(reiterarContra)){
            Toast.makeText(this,"Las contraseñas no coinciden!",Toast.LENGTH_SHORT).show();
            return;

        }
        //verificamos que el usuario no existe en nuestra Base de Datos
        if (usuarioExiste(email)){
            Toast.makeText(this,"El usuario ya existe!", Toast.LENGTH_SHORT).show();
            return;
        }

        long resultado = insertarUsuario(nombre,email,contraseña);

        if (resultado != -1){
            Toast.makeText(this,"Usuario registrado con exito!",Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(this,"Error al registrar el usuario!",Toast.LENGTH_SHORT).show();
        }
    }

    //creamos un metodo para verificar en el banco si ya existe un usuario registrado
    private boolean usuarioExiste(String email){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Registros", new String[]{"id"}, "email = ?",
                new String[]{email}, null, null, null);
        boolean existe = cursor.moveToFirst();
        cursor.close();
        return existe;

    }
    //creamos un metodo para ingresar los registros a nuestra base de datos
    private long insertarUsuario(String nombre,String email, String contraseña){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre",nombre);
        values.put("email",email);
        values.put("contraseña",contraseña);
        return  db.insert("Registros",null,values);

    }

    private void limpiarCampos(){
        nombreUser.setText("");
        emailUser.setText("");
        contrasenaUser.setText("");
        reiterarContraseña.setText("");
    }

}

//