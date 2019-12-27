package com.example.appedu.Release;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appedu.CardTask;
import com.example.appedu.R;
import com.example.appedu.TaskActivity;
import com.example.appedu.TaskViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageContentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<CardTask> list;
    private Toolbar toolbar;
    private String token;
    private Button enviar;

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
        setContentView(R.layout.activity_message_content);
        usuario = getIntent().getStringExtra("usuario");
        token = getIntent().getStringExtra("token");
        recyclerView = findViewById(R.id.recycler_view_message);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<CardTask>();
        enviar = findViewById(R.id.btn_create_message);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageContentActivity.this, MessageActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        toolbar = findViewById(R.id.toolbar_message);
        toolbar.setTitle("Comunicados");
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference().child("Mensaje").child(token).child(mAuth.getUid()).child(usuario);
        rootRef.keepSynced(true);

        options = new FirebaseRecyclerOptions.Builder<CardTask>().setQuery(rootRef, CardTask.class).build();

        adapter = new FirebaseRecyclerAdapter<CardTask, TaskViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final TaskViewHolder taskViewHolder, int i, @NonNull CardTask cardTask) {

                taskViewHolder.nombre.setText(cardTask.getNombre());
                taskViewHolder.tarea.setText(cardTask.getTarea());
            }

            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new TaskViewHolder(LayoutInflater.from(MessageContentActivity.this).inflate(R.layout.task_row, parent, false));
            }
        };

        recyclerView.setAdapter(adapter);
    }
}
