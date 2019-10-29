package com.example.appedu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegistroRoles extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_roles);
    }
    public void continuar(View view){

        Intent i =new Intent(this,SesionIniciada.class);
        startActivity(i);
        Toast.makeText(this,"Bienvenido",Toast.LENGTH_LONG).show();
    }

}
