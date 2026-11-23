package com.example.p1_crud_diskitos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    //Nombrar la base de datos
    public static final String NombreBD = "BaseDeDatos";

    //Constructor
    public DataBaseHelper(Context context) {
        super(context, NombreBD, null, 1);
    }

    //Crear tabla
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE tabla ("+
                "clave TEXT PRIMARY KEY,"+
                "titulo TEXT,"+
                "costo REAL)");
    }

    //Actualizar tabla
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tabla");
        onCreate(db);

    }
    //Insertar datos 
    
    public boolean Insertar (String clave, String titulo, String costo){
       SQLiteDatabase db = getWritableDatabase();

        ContentValues datos = new ContentValues();
        datos.put ("clave",clave);
        datos.put("titulo",titulo);
        datos.put("costo",costo);

        long resultado = db.insert("tabla",null,datos);
        return resultado != -1;

    }

    public Cursor Consultar (String clave){
        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM tabla WHERE clave= ?",
                new String[]{clave}
        );
    }
}
