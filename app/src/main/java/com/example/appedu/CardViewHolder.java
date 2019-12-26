package com.example.appedu;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardViewHolder extends RecyclerView.ViewHolder {
    public TextView curso, materia, profesor;
    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        curso = itemView.findViewById(R.id.text_card_curso);
        materia = itemView.findViewById(R.id.text_card_materia);
        profesor = itemView.findViewById(R.id.text_card_profesor);
    }
}
