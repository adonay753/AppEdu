package com.example.appedu;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    public TextView nombre, tarea;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);

        nombre = itemView.findViewById(R.id.txt_task_title);
        tarea = itemView.findViewById(R.id.txt_task_description);

    }
}
