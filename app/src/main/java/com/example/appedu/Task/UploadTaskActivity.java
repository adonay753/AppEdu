package com.example.appedu.Task;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appedu.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadTaskActivity extends AppCompatActivity {

    private EditText editPdfname;
    private EditText editDescripcion;
    private Button btn_upload;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_task);

        editPdfname=(EditText)findViewById(R.id.txtTituloProfe);
        editDescripcion=findViewById(R.id.txtTarea);
        btn_upload=(Button)findViewById(R.id.btn_subirArchivoEstudiante);

        storageReference= FirebaseStorage.getInstance().getReference();
        String dato_recibido = getIntent().getStringExtra("token");
        databaseReference= FirebaseDatabase.getInstance().getReference("Tarea").child(dato_recibido);///referencia de la base de datos cambiarlo por otro

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectPDFFile();
            }
        });

    }

    private void selectPDFFile() {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF File"),1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK ){//&& data!=null && data.getData()!=null){
            uploadPDFFile(data.getData());

        }



    }


    private void uploadPDFFile(Uri data) {

        final ProgressDialog progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("cargando..");
        progressDialog.show();
        StorageReference reference=storageReference.child(editPdfname.getText().toString()+"/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri= taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete());
                        Uri url=uri.getResult();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-ssssss");
                        String currentDateandTime = simpleDateFormat.format(new Date());
                        UploadPDF uploadPDF=new UploadPDF(editPdfname.getText().toString(),editDescripcion.getText().toString(),url.toString());
                        databaseReference.child(databaseReference.child(currentDateandTime).getKey()).setValue(uploadPDF);

                        Toast.makeText(UploadTaskActivity.this,"FileUploaded",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                double progress=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();

                progressDialog.setMessage("Subiendo: "+(int)progress+"%");

            }
        });

    }

}
