package com.example.appedu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class registroRoles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_roles);
    }
    public void regisProf (View view) {
        Intent i= new Intent(this,regisProf.class);
        startActivity(i);
        Toast.makeText(this, "Registre sus datos",Toast.LENGTH_SHORT).show();
    }
    public void regisAlum (View view) {
        Intent i= new Intent(this,regisAlumPad.class);
        startActivity(i);
        Toast.makeText(this, "Registre Los datos de su Hijo",Toast.LENGTH_SHORT).show();
    }
    public void regisPad (View view) {
        Intent i= new Intent(this,regisAlumPad.class);
        startActivity(i);
        Toast.makeText(this, "Ingrese el resto de sus datos",Toast.LENGTH_SHORT).show();
    }
}
