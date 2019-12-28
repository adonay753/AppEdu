package com.example.appedu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class SelectActivity extends AppCompatActivity {

    private Button alumno, padre;
    private Toolbar toolbar;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        token = getIntent().getStringExtra("token");

        toolbar = findViewById(R.id.toolbar_select);
        toolbar.setTitle("SELECIONAR OPCIÓN");
        setSupportActionBar(toolbar);

        alumno = findViewById(R.id.btn_select_alumno);
        padre = findViewById(R.id.btn_select_padre);

        alumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, ListActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });
        padre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, ListFatherActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });

    }
}
