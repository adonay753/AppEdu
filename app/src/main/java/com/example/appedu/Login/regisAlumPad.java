package com.example.appedu.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appedu.R;
import com.example.appedu.mainSesion;

public class regisAlumPad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_alum_pad);
    }
    public void regisAlumP (View view) {
        Intent i= new Intent(this, mainSesion.class);
        startActivity(i);
        Toast.makeText(this, "Bienvenido",Toast.LENGTH_SHORT).show();
    }
}
