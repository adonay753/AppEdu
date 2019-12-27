package com.example.appedu.SubirArchivo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appedu.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SubirActividadActivity extends AppCompatActivity {
    private EditText titulo,descripcion;
    private Button subir;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_actividad);
        titulo=findViewById(R.id.txtTituloActividad);
        descripcion=findViewById(R.id.txtDescripcionActividad);
        subir=findViewById(R.id.btn_PublicarActividad);
        storageReference= FirebaseStorage.getInstance().getReference();
        //String dato_recibido=this.getIntent().getStringExtra("token");
        //databaseReference= FirebaseDatabase.getInstance().getReference("Tarea").child(dato_recibido);
        databaseReference= FirebaseDatabase.getInstance().getReference("Tarea").child("madara");
        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publicar();
            }
        });
    }

    private void publicar() {
        UploadActivity uploadActivity;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-ssssss");
        String currentDateandTime = simpleDateFormat.format(new Date());
        uploadActivity=new UploadActivity(titulo.getText().toString(),descripcion.getText().toString());
        databaseReference.child(databaseReference.child(currentDateandTime).getKey()).setValue(uploadActivity);

        Toast.makeText(SubirActividadActivity.this,"FileUploaded",Toast.LENGTH_SHORT).show();
        titulo.setText("");
        descripcion.setText("");
    }


}
