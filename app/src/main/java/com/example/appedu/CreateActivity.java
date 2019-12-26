package com.example.appedu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateActivity extends AppCompatActivity {
    private TextView curso, materia, clave;
    private Button crear;
    private DatabaseReference cursosRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        curso = findViewById(R.id.txt_curso);
        materia = findViewById(R.id.txt_materia);
        clave = findViewById(R.id.txt_clave);
        crear = findViewById(R.id.btn_crear);
        cursosRef = FirebaseDatabase.getInstance().getReference().child("cursos");
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
