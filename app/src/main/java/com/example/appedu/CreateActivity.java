package com.example.appedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appedu.Login.MainActivity;
import com.example.appedu.SubirArchivo.SubirActividadActivity;
import com.example.appedu.SubirArchivo.SubirArchivoEstudianteActivity;
import com.example.appedu.SubirArchivo.SubirEsActivity;
import com.example.appedu.SubirArchivo.SubirTareaActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.UUID;

public class CreateActivity extends AppCompatActivity {
    private EditText curso, materia, clave;
    private Button crear;
    private String token;
    private Button subirArchivo;//---------------------------------------
    private DatabaseReference cursosRef;
    private FirebaseAuth usuario;
    private  Button publicar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        curso = findViewById(R.id.txt_curso);
        materia = findViewById(R.id.txt_materia);
        clave = findViewById(R.id.txt_clave);
        crear = findViewById(R.id.btn_crear);
        subirArchivo=findViewById(R.id.btn_SubirArchivo);//--------------------------------
        token = String.valueOf(UUID.randomUUID());
        clave.setText(token);
        publicar=findViewById(R.id.btn_PublicarActividad);//----------------------
        cursosRef = FirebaseDatabase.getInstance().getReference();
        usuario = FirebaseAuth.getInstance();

        subirArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), SubirArchivoEstudianteActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursosRef.child("Usuarios").child(usuario.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String nombre = dataSnapshot.child("Nombres").getValue().toString();
                        nombre += " " + dataSnapshot.child("Apellidas").getValue().toString();
                        HashMap<String, String> mapa = new HashMap<>();
                        mapa.put("profesor", nombre);
                        mapa.put("materia", materia.getText().toString());
                        mapa.put("curso", curso.getText().toString());
                        cursosRef.child("CursosProfesor").child(usuario.getUid()).child(token).setValue(mapa);
                        cursosRef.child("Token").child(token).setValue(mapa);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                changeMainSeci();
            }
        });
    }

    private void changeMainSeci() {
        Intent intent = new Intent(CreateActivity.this, mainSeci.class);
        intent.putExtra("rol", "Profesor");
        startActivity(intent);
        finish();
    }
}
