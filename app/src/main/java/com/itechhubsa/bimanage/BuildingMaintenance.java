package com.itechhubsa.bimanage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itechhubsa.bimanage.Pojos.Fault;

public class BuildingMaintenance extends AppCompatActivity implements View.OnClickListener {
    String _fault;
    EditText etSpecify, etDescription;
    TextView tvFault;
    private boolean _other;
    private ImageView imgReportProof;
    private Uri imageUri;
    private static final int CAMERA_REQUEST = 2017;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.building_maintenence);
        initialize();
        _other = getIntent().getBooleanExtra("_other", false);
        if (_other) {
            etSpecify.setVisibility(View.VISIBLE);
        }
        _fault = getIntent().getStringExtra("_fault");
        tvFault.setText(_fault);
    }

    void initialize() {
        Button btnCapture = findViewById(R.id.btn_capture_building_image);
        Button btnSubmitFault = findViewById(R.id.btn_submit_building_fault);
        etSpecify = findViewById(R.id.specify_fault);
        etDescription = findViewById(R.id.fault_description);
        tvFault = findViewById(R.id.tvFault);
        imgReportProof = findViewById(R.id.img_building_fault);

        btnCapture.setOnClickListener(this);
        btnSubmitFault.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), Home.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_capture_building_image:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
            case R.id.btn_submit_building_fault:
                writeFault();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            try {
                imageUri = data.getData();
                imgReportProof.setImageURI(imageUri);
                imgReportProof.setRotation(imgReportProof.getRotation() + 90);
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Internal storage is full...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void writeFault() {
        if (TextUtils.isEmpty(etDescription.getText())) {
            etDescription.setError("Please specify the fault...");
        } else {
            final StorageReference filePath = storage.getReference().child("Gallery").child(imageUri.getLastPathSegment());

            filePath.putFile(imageUri).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(getBaseContext(), "Fault not captured...", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    filePath.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            // Continue with the task to get the download URL
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                assert downloadUri != null;
                                if (_other) {
                                    FirebaseDatabase.getInstance().getReference("Faults").push().setValue(new Fault(etDescription.getText().toString(), 100000,
                                            "Outside units", etSpecify.getText().toString(), downloadUri.toString()));
                                    Toast.makeText(getBaseContext(), "Fault message sent...", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getBaseContext(), Home.class));
                                    finish();
                                } else {
                                    FirebaseDatabase.getInstance().getReference("Faults").push().setValue(new Fault(etDescription.getText().toString(), 100000,
                                            "Outside units", _fault, downloadUri.toString()));
                                    Toast.makeText(getBaseContext(), "Fault message sent...", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getBaseContext(), Home.class));
                                    finish();
                                }
                            } else {
                                // Handle failures
                                // ...
                            }
                        }
                    });
                }
            });
        }
    }
}
