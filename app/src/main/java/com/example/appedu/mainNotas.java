package com.example.appedu;

import android.content.Intent;
import android.icu.util.ValueIterator;
import android.os.Bundle;

import com.example.appedu.Login.registroRoles;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;



public class mainNotas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Subir  Notas", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }
    public void verNotas (View view){

        Intent i= new Intent(this, verNotas.class);
        startActivity(i);

    }

}
