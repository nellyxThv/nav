package com.example.p1_crud_diskitos;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText clave, nombre, precio;
    private Button altas, consultas;
    private DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initViews();
        activarCampos(true);
        altas.setOnClickListener(v->manejarAltas());
        consultas.setOnClickListener(v-> manejarConsultas());

    }

    private void initViews(){
        clave=findViewById(R.id.clave);
        nombre=findViewById(R.id.nombre);
        precio=findViewById(R.id.precio);
        consultas=findViewById(R.id.consultas);
        altas=findViewById(R.id.altas);

        db = new DataBaseHelper(this);
    }

    private void manejarAltas(){
        // Se usa equalsIgnoreCase para comparar sin importar mayúsculas o minúsculas ("Altas" o "altas")
        if (altas.getText().toString().equalsIgnoreCase("altas")){
            prepararAltas();
        }else{
            guardarRegistro();
        }

    }
    private void manejarConsultas(){
        // Se usa equalsIgnoreCase para comparar sin importar mayúsculas o minúsculas ("Consultas" o "consultas")
        if (consultas.getText().toString().equalsIgnoreCase("consultas")){
            prepararConsultas();
        }else{
            consultarRegistro();
        }
    }
    private void prepararAltas(){
        //Activar campos (todos)
        activarCampos(true);
        altas.setText("Agregar");
        consultas.setEnabled(false);
    }
    private void prepararConsultas(){
        consultas.setText("Buscar");
        clave.setEnabled(true);
        nombre.setEnabled(false);
        precio.setEnabled(false);
        altas.setEnabled(false);
    }
    private void guardarRegistro(){
        String Clave = clave.getText().toString();
        String Nombre = nombre.getText().toString();
        String Precio = precio.getText().toString();

        double costo= 0;
        if (!Precio.isEmpty()){
            try{
                costo =Double.parseDouble(Precio);
            }catch (Exception ignored){
            }
        }
        db.Insertar(Clave,Nombre,Precio);

        limpiarCampos();
        restablecerCampos();

    }
    private void consultarRegistro(){
        String Clave = clave.getText().toString();

        try(Cursor cursor = db.Consultar(Clave)){
            if(cursor!=null&& cursor.moveToFirst()){
                nombre.setText(cursor.getString(1));
                precio.setText(cursor.getString(2));
            }else{
                Toast.makeText(this, "No existe el registro", Toast.LENGTH_SHORT).show();
            }
        }
        restablecerCampos();
    }
    private void limpiarCampos(){
        nombre.setText("");
        clave.setText("");
        precio.setText("");
    }

    private void activarCampos(boolean estado){
        nombre.setEnabled(estado);
        clave.setEnabled(estado);

        precio.setEnabled(estado);
    }

    private void restablecerCampos (){
        //Se regresa todo a como estaba en el principio
        //Botones con el nombre original, campos de escritura desactivados
        activarCampos(false);
        altas.setEnabled(true);
        altas.setText("altas");
        consultas.setEnabled(true);
        consultas.setText("Consultas");

    }
}