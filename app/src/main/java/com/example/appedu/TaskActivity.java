package com.example.appedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appedu.Task.UploadTaskActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<CardTask> list;
    private Toolbar toolbar;
    private String token;

    private FirebaseRecyclerOptions<CardTask> options;
    private FirebaseRecyclerAdapter<CardTask, TaskViewHolder> adapter;
    private DatabaseReference rootRef;
    private FirebaseAuth mAuth;
    private String usuario;
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        usuario = getIntent().getStringExtra("usuario");
        token = getIntent().getStringExtra("token");
        recyclerView = findViewById(R.id.recycler_view_task);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<CardTask>();

        toolbar = findViewById(R.id.toolbar_task);
        toolbar.setTitle("Tareas");
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference().child("Tarea").child(token).child(mAuth.getUid()).child(usuario);
        rootRef.keepSynced(true);

        options = new FirebaseRecyclerOptions.Builder<CardTask>().setQuery(rootRef, CardTask.class).build();

        adapter = new FirebaseRecyclerAdapter<CardTask, TaskViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final TaskViewHolder taskViewHolder, int i, @NonNull CardTask cardTask) {

                taskViewHolder.nombre.setText(cardTask.getNombre());
                taskViewHolder.tarea.setText(cardTask.getTarea());
                final int position = i;
                taskViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getRef(position).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Intent intent = new Intent();
                                intent.setData(Uri.parse(dataSnapshot.child("url").getValue().toString()));
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });

            }

            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new TaskViewHolder(LayoutInflater.from(TaskActivity.this).inflate(R.layout.task_row, parent, false));
            }
        };

        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.munu_tareas, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.create_task) {
            Intent intent = new Intent(TaskActivity.this, UploadTaskActivity.class);
            intent.putExtra("token", token);
            intent.putExtra("usuario", usuario);
            startActivity(intent);
        }
        if (id == R.id.deliver_task) {
            Intent intent = new Intent(TaskActivity.this, UploadTaskActivity.class);
            intent.putExtra("token", token);
            intent.putExtra("usuario", usuario);
            startActivity(intent);
        }
        return true;
    }
}
