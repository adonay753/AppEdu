package com.example.appedu.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appedu.R;
import com.example.appedu.mainSeci;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
    }
    public void logonboton (View view) {
        Intent i = new Intent(this, mainSeci.class);

        startActivity(i);
        Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
    }

    public void regis (View view) {
        Intent i= new Intent(this,registroRoles.class);
        startActivity(i);
        Toast.makeText(this, "Bienvenido",Toast.LENGTH_SHORT).show();
    }
}
