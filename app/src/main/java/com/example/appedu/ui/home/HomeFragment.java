package com.example.appedu.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appedu.Card;
import com.example.appedu.CreateActivity;
import com.example.appedu.ListActivity;
import com.example.appedu.Login.MainActivity;
import com.example.appedu.R;
import com.example.appedu.mainSecipad;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {
    private RecyclerView list;
    private Button btn;
    private DatabaseReference base;
    private FirebaseAuth usuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        usuario = FirebaseAuth.getInstance();
        base = FirebaseDatabase.getInstance().getReference().child("CursosProfesor").child(usuario.getUid());
        base.keepSynced(true);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        list = root.findViewById(R.id.main_recycler);
        btn = root.findViewById(R.id.btn_enter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplication(), CreateActivity.class);
                startActivity(intent);
            }
        });
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        return root;

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Card> options =
                new FirebaseRecyclerOptions.Builder<Card>()
                        .setQuery(base, Card.class)
                        .build();
        FirebaseRecyclerAdapter<Card, CardViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Card, CardViewHolder>
                (options) {
            @Override
            protected void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int i, @NonNull Card card) {
                cardViewHolder.setCurso(card.getCurso());
                cardViewHolder.setMateria(card.getMateria());
                cardViewHolder.setProfesor(card.getProfesor());
                cardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity().getApplication(), "imprimir", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @NonNull
            @Override
            public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_row, parent, false);

                return new CardViewHolder(view);
            }
        };
        list.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        View view;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }
        public void setCurso(String nombre){
            TextView curso = view.findViewById(R.id.text_card_curso);
            curso.setText(nombre);
        }
        public void setMateria(String nombre){
            TextView materia = view.findViewById(R.id.text_card_materia);
            materia.setText(nombre);
        }
        public void setProfesor(String nombre){
            TextView profesor = view.findViewById(R.id.text_card_profesor);
            profesor.setText(nombre);
        }
    }
}
