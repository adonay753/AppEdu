package com.example.appedu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void logonboton(View view) {
        Intent i = new Intent(this, SesionIniciada.class);
        startActivity(i);
        Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
    }
    public void regis (View view){
        Intent i =new Intent(this,RegistroRoles.class);
        startActivity(i);
        Toast.makeText(this,"Ingresa tus datos y elije tu Rol",Toast.LENGTH_LONG).show();
    }
}
