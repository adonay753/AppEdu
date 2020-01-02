package com.example.appedu.Release;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appedu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MessageAllActivity extends AppCompatActivity {

    private EditText message;
    private Button sendMessage;
    private ArrayList<String> userList;
    private String token;

    private DatabaseReference rootRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_all);
        userList = getIntent().getStringArrayListExtra("usuario");
        token = getIntent().getStringExtra("token");

        message = findViewById(R.id.txt_message_all);
        sendMessage = findViewById(R.id.btn_message_all);

        rootRef = FirebaseDatabase.getInstance().getReference("Mensaje").child(token);
        mAuth = FirebaseAuth.getInstance();

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-ssssss");
                String currentDateandTime = simpleDateFormat.format(new Date());
                for (String usuario: userList) {
                    HashMap<String, String> mapa = new HashMap<>();
                    mapa.put("nombre", message.getText().toString());
                    mapa.put("tarea", currentDateandTime);
                    rootRef.child(mAuth.getUid()).child(usuario).child(rootRef.child(currentDateandTime).getKey()).setValue(mapa);
                    rootRef.child(usuario).child(mAuth.getUid()).child(rootRef.child(currentDateandTime).getKey()).setValue(mapa);
                }
            }
        });
    }
}
