package com.example.appedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        token = getIntent().getStringExtra("token");
        recyclerView = findViewById(R.id.recycler_view_task);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<CardTask>();

        toolbar = findViewById(R.id.toolbar_task);
        toolbar.setTitle("Tareas");
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference().child("uploads");
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

                        Toast.makeText(TaskActivity.this, getRef(position).getKey(), Toast.LENGTH_LONG).show();
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
            //Intent intent = new Intent(TaskActivity.this, SubirEsActivity.class);
            //intent.putExtra("token", token);
            //startActivity(intent);
        } else if (id == R.id.deliver_task) {
            //Intent intent = new Intent(TaskActivity.this, SubirTarea.class);
            //intent.putExtra("token", token);
            //startActivity(intent);
        }
        return true;
    }
}
