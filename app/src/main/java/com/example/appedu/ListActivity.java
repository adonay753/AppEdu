package com.example.appedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> listFiles;

    private DatabaseReference rootRef;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        token = getIntent().getStringExtra("token");
        rootRef = FirebaseDatabase.getInstance().getReference();

        listView = findViewById(R.id.list_class);
        listFiles = new ArrayList<>();
        viewAllFile();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this, TaskActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });

    }

    private void viewAllFile() {

        rootRef.child("CursosAlumno").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot idAlumno : dataSnapshot.getChildren()) {
                    rootRef.child("CursosAlumno").child(idAlumno.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot idToken: dataSnapshot.getChildren()) {
                                if(idToken.getKey().equals(token)) {
                                    rootRef.child("Usuarios").child(idAlumno.getKey()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String nombre = dataSnapshot.child("Nombres").getValue().toString();
                                            nombre += " " + dataSnapshot.child("Apellidas").getValue().toString();
                                            listFiles.add(nombre);

                                            String[] uploads = new String[listFiles.size()];

                                            for (int i=0; i<uploads.length; i++) {
                                                uploads[i] = listFiles.get(i);
                                            }
                                            ArrayAdapter<String> arr = new ArrayAdapter<String>(getApplicationContext(), R.layout.lista_row, uploads);
                                            listView.setAdapter(arr);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
