package com.example.appedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JoinActivity extends AppCompatActivity {
    private DatabaseReference rootRef;
    private FirebaseAuth mAuth;

    private Button join;
    private EditText key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        join = findViewById(R.id.btn_join);
        key = findViewById(R.id.txt_key_join);

        rootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootRef.child("Token").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot token: dataSnapshot.getChildren()) {
                            if (token.getKey().equals(key.getText().toString())) {
                                System.out.println(token.getKey());
                                rootRef.child("CursosAlumno").child(mAuth.getUid()).child(token.getKey()).setValue(token.getValue());
                                sendHome();
                                
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    private void sendHome() {
        Intent intent = new Intent(JoinActivity.this, mainSeciest.class);
        intent.putExtra("rol", "Alumno");
        startActivity(intent);
        finish();
    }
}
