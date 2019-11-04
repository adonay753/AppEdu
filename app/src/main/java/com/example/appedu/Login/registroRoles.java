package com.example.appedu.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appedu.R;

public class registroRoles extends AppCompatActivity {


    Spinner selecciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_roles);

        selecciones=findViewById(R.id.idspinner);

        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,
                R.array.arrayRoles,android.R.layout.simple_spinner_item);

        selecciones.setAdapter(adapter);

    }
    /*public void regisProf (View view) {
        Intent i= new Intent(this,regisProf.class);
        startActivity(i);
        Toast.makeText(this, "Registre sus datos",Toast.LENGTH_SHORT).show();
    }
    public void regisAlum (View view) {
        Intent i= new Intent(this,regisAlumPad.class);
        startActivity(i);
        Toast.makeText(this, "Registre Los datos de su Hijo",Toast.LENGTH_SHORT).show();
    }
    public void regisPad (View view) {
        Intent i= new Intent(this,regisAlumPad.class);
        startActivity(i);
        Toast.makeText(this, "Ingrese el resto de sus datos",Toast.LENGTH_SHORT).show();*/
    }

