package com.example.appedu.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appedu.Card;
import com.example.appedu.CreateActivity;
import com.example.appedu.JoinActivity;
import com.example.appedu.Login.MainActivity;
import com.example.appedu.R;
import com.example.appedu.Release.MessageContentActivity;
import com.example.appedu.SelectActivity;
import com.example.appedu.TaskActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {
    private RecyclerView list;
    private Button btn, join, exit;
    private DatabaseReference base;
    private FirebaseAuth usuario;
    private String rol;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rol = getActivity().getIntent().getStringExtra("rol");

        usuario = FirebaseAuth.getInstance();

        if (rol.equals("Profesor"))
            base = FirebaseDatabase.getInstance().getReference().child("CursosProfesor").child(usuario.getUid());
        else if (rol.equals("Alumno"))
            base = FirebaseDatabase.getInstance().getReference().child("CursosAlumno").child(usuario.getUid());
        else if (rol.equals("Padre"))
            base = FirebaseDatabase.getInstance().getReference().child("CursosPadre").child(usuario.getUid());

        base.keepSynced(true);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        list = root.findViewById(R.id.main_recycler);
        btn = root.findViewById(R.id.btn_enter);
        join = root.findViewById(R.id.btn_unirse);
        exit = root.findViewById(R.id.btn_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplication(), JoinActivity.class);
                startActivity(intent);
            }
        });
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

                final int position = i;
                cardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rol.equals("Profesor")){
                            Intent intent = new Intent(getActivity(), SelectActivity.class);
                            intent.putExtra("token", getRef(position).getKey());
                            startActivity(intent);
                        } else if (rol.equals("Alumno")) {
                            final String token = getRef(position).getKey();
                            DatabaseReference otro = FirebaseDatabase.getInstance().getReference();
                            otro.child("CursosProfesor").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot idProfesor: dataSnapshot.getChildren()) {
                                        for (DataSnapshot idToken: idProfesor.getChildren()) {
                                            if (idToken.getKey().equals(token)) {
                                                Intent intent = new Intent(getActivity(), TaskActivity.class);
                                                intent.putExtra("token", token);
                                                intent.putExtra("usuario", idProfesor.getKey());
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        } else if (rol.equals("Padre")) {
                            final String token = getRef(position).getKey();
                            DatabaseReference otro = FirebaseDatabase.getInstance().getReference();
                            otro.child("CursosProfesor").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot idProfesor: dataSnapshot.getChildren()) {
                                        for (DataSnapshot idToken: idProfesor.getChildren()) {
                                            if (idToken.getKey().equals(token)) {
                                                Intent intent = new Intent(getActivity(), MessageContentActivity.class);
                                                intent.putExtra("token", token);
                                                intent.putExtra("usuario", idProfesor.getKey());
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
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

    @Override
    public void onStop() {
        super.onStop();
    }

    private static class CardViewHolder extends RecyclerView.ViewHolder {

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
