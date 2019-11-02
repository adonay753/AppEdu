package com.example.appedu.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appedu.R;
import com.example.appedu.mainSesion;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void logonboton (View view) {
        Intent i = new Intent(this, mainSesion.class);
        startActivity(i);
        Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
    }

    public void regis (View view) {
        Intent i= new Intent(this,registroRoles.class);
        startActivity(i);
        Toast.makeText(this, "Bienvenido",Toast.LENGTH_SHORT).show();
    }
}