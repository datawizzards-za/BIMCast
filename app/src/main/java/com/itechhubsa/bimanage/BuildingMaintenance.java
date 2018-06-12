package com.itechhubsa.bimanage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
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
        Button btnSubmitFault =  findViewById(R.id.btn_submit_building_fault);
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
            if (imageUri != null) {
                StorageReference filePath = FirebaseStorage.getInstance().getReference().child("Gallery").child(imageUri.getLastPathSegment());
                //Toast.makeText(getBaseContext(), imageUri.toString(), Toast.LENGTH_SHORT).show();
                filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        @SuppressWarnings("VisibleForTests") Uri downloadUri = taskSnapshot.getDownloadUrl();
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
                    }
                });
            } else {
                Toast.makeText(getBaseContext(), "Fault not captured...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
