package com.example.appedu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SelectActivity extends AppCompatActivity {

    private Button alumno, padre;
    private TextView codigo;
    private Toolbar toolbar;
    private String token;
    private String rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        token = getIntent().getStringExtra("token");
        rol = getIntent().getStringExtra("rol");
        codigo = findViewById(R.id.class_key);
        codigo.setText(token);

        toolbar = findViewById(R.id.toolbar_select);
        toolbar.setTitle("SELECIONAR OPCIÓN");
        setSupportActionBar(toolbar);

        alumno = findViewById(R.id.btn_select_alumno);
        padre = findViewById(R.id.btn_select_padre);

        alumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, ListActivity.class);
                intent.putExtra("rol", rol);
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
