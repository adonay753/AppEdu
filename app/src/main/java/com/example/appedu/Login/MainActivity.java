package com.example.appedu.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appedu.R;
import com.example.appedu.mainSeci;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference rootReference;

    private EditText email;
    private EditText contrasenia;
    private Button ingresar;
    private TextView registrarse;
    private String rol;
    private ProgressDialog progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        rootReference = FirebaseDatabase.getInstance().getReference();

        email = findViewById(R.id.editText2);
        contrasenia = findViewById(R.id.editText);
        ingresar = findViewById(R.id.button2);
        registrarse = findViewById(R.id.registry);

        rootReference.child("Usuarios").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    rol = dataSnapshot.child("Rol").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        progreso = new ProgressDialog(MainActivity.this);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarUsuario();
            }
        });

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            iniciar();
        }

    }

    private void verificarUsuario() {

        String correo = email.getText().toString();
        String password = contrasenia.getText().toString();

        if (TextUtils.isEmpty(correo)) {
            Toast.makeText(this, "Por favor ingrese su email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor ingrese su contraseña", Toast.LENGTH_SHORT).show();
        } else {

            progreso.setTitle("Iniciando Sesion");
            progreso.setMessage("Por favor espere");
            progreso.setCanceledOnTouchOutside(true);
            progreso.show();

            mAuth.signInWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        iniciar();
                        Toast.makeText(MainActivity.this, "Inicio de Sesion Exitosa", Toast.LENGTH_SHORT).show();
                        progreso.dismiss();
                    } else {
                        String mensaje = task.getException().toString();
                        Toast.makeText(MainActivity.this, "Error: " + mensaje, Toast.LENGTH_SHORT).show();
                        progreso.dismiss();
                    }
                }
            });

        }
    }

    private void iniciar() {
        if (rol.equals("Profesor")) {

        } else if (rol.equals("Alumno")) {

        } else if (rol.equals("Padre")) {

        }
        //para cada usas esta forma de intent para que no pueda vover atras
        Intent intent = new Intent(MainActivity.this, mainSeci.class);
        startActivity(intent);
        finish();
    }

    private void registrarUsuario() {
        Intent intent = new Intent(MainActivity.this,registroRoles.class);
        startActivity(intent);
    }
}
