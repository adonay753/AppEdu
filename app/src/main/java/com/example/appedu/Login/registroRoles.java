package com.example.appedu.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appedu.R;
import com.example.appedu.mainSeci;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class registroRoles extends AppCompatActivity {

    private Spinner selecciones;

    private EditText email;
    private EditText nombres;
    private EditText apellidos;
    private EditText contrasenia;
    private EditText confirmarContrasenia;
    private EditText materia;
    private EditText ci;
    private Button continuar;
    private String rol;

    private FirebaseAuth mAuth;
    private DatabaseReference rootReference;

    private ProgressDialog progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro_roles);

        mAuth = FirebaseAuth.getInstance();
        rootReference = FirebaseDatabase.getInstance().getReference();

        email = findViewById(R.id.editText5);
        nombres = findViewById(R.id.editText1);
        apellidos = findViewById(R.id.editText2);
        contrasenia = findViewById(R.id.editText3);
        confirmarContrasenia = findViewById(R.id.editText4);
        materia = (EditText) findViewById(R.id.Mat);
        continuar = (Button) findViewById(R.id.cont);
        ci = (EditText) findViewById(R.id.ci);

        progreso = new ProgressDialog(registroRoles.this);

        final String selec,prof,alumn,padr;
        selec = "-Seleccionar-";
        prof = "Profesor";
        alumn = "Alumno";
        padr = "Padre";

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarNuevoUsuario();
            }
        });

        //Configuracion del Spinner para que imprima el array de los recursos en values

        selecciones=findViewById(R.id.idspinner);

        final ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,
                R.array.arrayRoles,android.R.layout.simple_spinner_item);

        selecciones.setAdapter(adapter);

        selecciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).toString().equals(selec)) {

                    materia.setVisibility(view.GONE);
                    continuar.setVisibility(View.GONE);
                    ci.setVisibility(View.GONE);
                }
                if (parent.getItemAtPosition(position).toString().equals(prof)) {

                    materia.setVisibility(view.VISIBLE);
                    continuar.setVisibility(View.VISIBLE);
                    ci.setVisibility(View.GONE);
                    rol = prof;
                }
                if (parent.getItemAtPosition(position).toString().equals(alumn)) {

                    materia.setVisibility(View.GONE);
                    continuar.setVisibility(View.VISIBLE);
                    ci.setVisibility(View.VISIBLE);
                    rol = alumn;

                }
                if (parent.getItemAtPosition(position).toString().equals(padr)) {

                    Snackbar.make(view, "Ingrese el C.I. del Alumno", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    materia.setVisibility(View.GONE);
                    continuar.setVisibility(View.VISIBLE);
                    ci.setVisibility(View.VISIBLE);
                    rol = padr;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
    }

    private void registrarNuevoUsuario() {

        String correo = email.getText().toString();
        String password = contrasenia.getText().toString();

        if (TextUtils.isEmpty(correo)) {
            Toast.makeText(this, "Por favor ingrese su email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor Ingrese su contraseña", Toast.LENGTH_SHORT).show();
        }
        if (!password.equals(confirmarContrasenia.getText().toString())) {
            Toast.makeText(this, "Por favor verifique su contraseña", Toast.LENGTH_SHORT).show();
        } else {

            progreso.setTitle("Creando Cuenta");
            progreso.setMessage("Por favor espere, mientras creamos su cuenta");
            progreso.setCanceledOnTouchOutside(true);
            progreso.show();

            mAuth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String currentUsrID = mAuth.getCurrentUser().getUid();

                        HashMap<String, String> mapa = new HashMap<>();
                        mapa.put("Nombres", nombres.getText().toString());
                        mapa.put("Apellidas", apellidos.getText().toString());

                        if (rol.equals("Alumno") || rol.equals("Padre")) {
                            mapa.put("Cedula", ci.getText().toString());
                        } else {
                            mapa.put("Materia", materia.getText().toString());
                        }
                        mapa.put("Rol", rol);

                        rootReference.child("Usuarios").child(currentUsrID).setValue(mapa);
                        inicar();
                        Toast.makeText(registroRoles.this, "Cuenta Creada Exitosamente", Toast.LENGTH_SHORT).show();
                        progreso.dismiss();

                    } else {
                        String mensaje = task.getException().toString();
                        Toast.makeText(registroRoles.this, "Error: " + mensaje, Toast.LENGTH_SHORT).show();
                        progreso.dismiss();
                    }
                }
            });
        }
    }

    private void inicar() {
        if (rol.equals("Profesor")) {
            Toast.makeText(registroRoles.this, rol, Toast.LENGTH_SHORT).show();
        } else if (rol.equals("Alumno")) {
            Toast.makeText(registroRoles.this, rol, Toast.LENGTH_SHORT).show();

        } else if (rol.equals("Padre")) {
            Toast.makeText(registroRoles.this, rol, Toast.LENGTH_SHORT).show();

        }
        //para cada usas esta forma de intent para que no pueda vover atras
        Intent intent = new Intent(registroRoles.this, mainSeci.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}


