package com.example.appedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appedu.Release.MessageAllActivity;
import com.example.appedu.Release.MessageContentActivity;
import com.example.appedu.Task.UploadTaskAllActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListFatherActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private List<String> listFiles;
    private ArrayList<String> usuario;

    private DatabaseReference rootRef;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_father);
        toolbar = findViewById(R.id.toolbal_list_father);
        toolbar.setTitle("Listado de Padres");
        setSupportActionBar(toolbar);

        token = getIntent().getStringExtra("token");
        rootRef = FirebaseDatabase.getInstance().getReference();

        listView = findViewById(R.id.list_father);
        listFiles = new ArrayList<>();
        usuario = new ArrayList<>();

        viewAllFile();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListFatherActivity.this, MessageContentActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("usuario", usuario.get(position));
                startActivity(intent);
            }
        });
    }
    private void viewAllFile() {

        rootRef.child("CursosPadre").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot idAlumno : dataSnapshot.getChildren()) {
                    rootRef.child("CursosPadre").child(idAlumno.getKey()).addValueEventListener(new ValueEventListener() {
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
                                            usuario.add(idAlumno.getKey());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_all_send, menu);
        MenuItem mensaje = menu.findItem(R.id.send_task_all);
        mensaje.setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.send_message_all) {
            Intent intent = new Intent(ListFatherActivity.this, MessageAllActivity.class);
            intent.putExtra("token", token);
            intent.putStringArrayListExtra("usuario", usuario);
            startActivity(intent);
        }
        return true;
    }

}
