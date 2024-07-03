package com.example.barbershopapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBase extends SQLiteOpenHelper {
    private static final String TAG="BANCO";
    // Nombre de la base de datos
    private static final String DATABASE_NAME = "registros.db";
    private static final int DATABASE_VERSION = 1;

    public DataBase(Context context){

        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Definimos el SQL para crear la tabla "Registros"
        String CREATE_REGISTROS_TABLE = "CREATE TABLE IF NOT EXISTS Registros (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "contraseña TEXT NOT NULL" +
                ")";
        // Ejecutamos el SQL
        sqLiteDatabase.execSQL(CREATE_REGISTROS_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
