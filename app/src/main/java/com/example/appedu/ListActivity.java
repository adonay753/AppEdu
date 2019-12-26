package com.example.appedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appedu.ui.home.HomeFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private RecyclerView list;
    private DatabaseReference base;
    private FirebaseRecyclerOptions<Card> options;
    private FirebaseRecyclerAdapter<Card, CardViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        base = FirebaseDatabase.getInstance().getReference().child("Prueba");
        base.keepSynced(true);
        list = findViewById(R.id.main_recycler);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this));
        options = new FirebaseRecyclerOptions.Builder<Card>().setQuery(base, Card.class).build();

        adapter = new FirebaseRecyclerAdapter<Card, CardViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int i, @NonNull Card card) {
                cardViewHolder.curso.setText(card.getCurso());
                cardViewHolder.materia.setText(card.getMateria());
                cardViewHolder.profesor.setText(card.getProfesor());
                cardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ListActivity.this, "imprimir", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @NonNull
            @Override
            public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new CardViewHolder(LayoutInflater.from(ListActivity.this).inflate(R.layout.card_row, parent, false));
            }
        };

        list.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
