package com.example.appedu.Login;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.appedu.R;
import com.google.android.material.snackbar.Snackbar;


public class registroRoles extends AppCompatActivity {


    Spinner selecciones;
    EditText a;
    EditText c;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_roles);

        a = (EditText) findViewById(R.id.Mat);
        b = (Button) findViewById(R.id.cont);
        c = (EditText) findViewById(R.id.ci);

        final String selec,prof,alumn,padr;
        selec = "-Seleccionar-";
        prof = "Profesor";
        alumn = "Alumno";
        padr = "Padre";

        //Configuracion del Spinner para que imprima el array de los recursos en values

        selecciones=findViewById(R.id.idspinner);

        final ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,
                R.array.arrayRoles,android.R.layout.simple_spinner_item);

        selecciones.setAdapter(adapter);

        selecciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).toString().equals(selec)) {

                    a.setVisibility(view.GONE);
                    b.setVisibility(View.GONE);
                    c.setVisibility(View.GONE);
                }
                if (parent.getItemAtPosition(position).toString().equals(prof)) {

                    a.setVisibility(view.VISIBLE);
                    b.setVisibility(View.VISIBLE);
                    c.setVisibility(View.GONE);
                }
                if (parent.getItemAtPosition(position).toString().equals(alumn)) {

                    a.setVisibility(View.GONE);
                    b.setVisibility(View.VISIBLE);
                    c.setVisibility(View.VISIBLE);

                }
                if (parent.getItemAtPosition(position).toString().equals(padr)) {

                    Snackbar.make(view, "Ingrese el C.I. del Alumno", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    a.setVisibility(View.GONE);
                    b.setVisibility(View.VISIBLE);
                    c.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
    }
}


