package com.itechhubsa.bimanage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itechhubsa.bimanage.Pojos.Fault;

public class UnitMaintenance extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {
    private String _back_key_pressed, fault_location, specifying_fault_type;
    private static final int CAMERA_REQUEST = 2017;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private EditText etDescription, etUnitNo, etSpecifyLocation, etSpecifyFault;
    private ImageView imgReportProof;
    private Uri imageUri;
    private RadioGroup rgGroup;
    private boolean fault_other;
    RadioButton rbLounge, rbKitchen, rbBedroom, rbBathRoom, rbOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_maintenance);
        initialize();
        _back_key_pressed = getIntent().getStringExtra("_compare");
        fault_other = getIntent().getBooleanExtra("_other", false);
        if (fault_other) {
            etSpecifyFault.setVisibility(View.VISIBLE);
        }
        specifying_fault_type = getIntent().getStringExtra("problem");
    }

    void initialize() {
        Button btnCaptureImage = findViewById(R.id.btn_capture_image);
        Button btnSubmitReport = findViewById(R.id.btn_submit_fault);
        //
        etDescription = findViewById(R.id.unit_fault_description);
        etUnitNo = findViewById(R.id.fault_unit_number);
        etSpecifyLocation = findViewById(R.id.fault_unit_place);
        etSpecifyFault = findViewById(R.id.specify_unit_fault);
        //
        imgReportProof = findViewById(R.id.unit_fault_image_display);
        //
        rgGroup = findViewById(R.id.rg_fault_location);
        //
        rbBathRoom = findViewById(R.id.rb_bath_room);
        rbBedroom = findViewById(R.id.rb_bedroom);
        rbKitchen = findViewById(R.id.rb_kitchen);
        rbLounge = findViewById(R.id.rb_lounge);
        rbOther = findViewById(R.id.rb_other);
        //
        rgGroup.setOnCheckedChangeListener(this);
        //
        btnCaptureImage.setOnClickListener(this);
        btnSubmitReport.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (_back_key_pressed.equalsIgnoreCase("Electricity")) {
            startActivity(new Intent(getBaseContext(), Electricity.class));
            finish();
        } else if (_back_key_pressed.equalsIgnoreCase("Carpentry")) {
            startActivity(new Intent(getBaseContext(), Carpentry.class));
            finish();
        } else if (_back_key_pressed.equalsIgnoreCase("Plumbing")) {
            startActivity(new Intent(getBaseContext(), Plumbing.class));
            finish();
        } else {
            startActivity(new Intent(getBaseContext(), Home.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_capture_image:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
            case R.id.btn_submit_fault:
                Toast.makeText(getBaseContext(), "Clicked, and am about to save..", Toast.LENGTH_SHORT).show();
                addComment();
                break;
            default:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            try {
                imgReportProof.setImageURI(imageUri);
                imgReportProof.setRotation(imgReportProof.getRotation() + 90);
            } catch (Exception ex) {
                Toast.makeText(getBaseContext(), "Could not upload you image...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addComment() {
        StorageReference filePath = storage.getReference().child("Gallery").child(imageUri.getLastPathSegment());
        filePath.putFile(imageUri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(getBaseContext(), "Could not upload you comment...", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                //
                final StorageReference ref = storage.getReference().child(String.valueOf(taskSnapshot.getUploadSessionUri()));
                ref.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            assert downloadUri != null;
                            if (!TextUtils.isEmpty(etDescription.getText().toString())) {
                                /*
                                 ** Creating and storing an object on a database.
                                 ** When it mean a certain condition.
                                 */
                                if (fault_other) {
                                    if (fault_location != null) {
                                        if (specifying_fault_type.equalsIgnoreCase("Other specification")) {
                                            FirebaseDatabase.getInstance().getReference("Faults").push().setValue(new Fault(etDescription.getText().toString(),
                                                    Integer.parseInt(etUnitNo.getText().toString()), fault_location, etSpecifyFault.getText().toString(), downloadUri.toString()));
                                        } else {
                                            FirebaseDatabase.getInstance().getReference("Faults").push().setValue(new Fault(etDescription.getText().toString(),
                                                    Integer.parseInt(etUnitNo.getText().toString()), fault_location, specifying_fault_type, downloadUri.toString()));
                                        }
                                    } else {
                                        if (specifying_fault_type.equalsIgnoreCase("Other specification")) {
                                            FirebaseDatabase.getInstance().getReference("Faults").push().setValue(new Fault(etDescription.getText().toString(),
                                                    Integer.parseInt(etUnitNo.getText().toString()), etSpecifyLocation.getText().toString(), etSpecifyFault.getText().toString(), downloadUri.toString()));
                                        } else {
                                            FirebaseDatabase.getInstance().getReference("Faults").push().setValue(new Fault(etDescription.getText().toString(),
                                                    Integer.parseInt(etUnitNo.getText().toString()), etSpecifyLocation.getText().toString(), specifying_fault_type, downloadUri.toString()));
                                        }
                                    }
                                }

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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_bath_room:
                etSpecifyLocation.setVisibility(View.GONE);
                fault_location = rbBathRoom.getText().toString();
                break;
            case R.id.rb_bedroom:
                etSpecifyLocation.setVisibility(View.GONE);
                fault_location = rbBedroom.getText().toString();
                break;
            case R.id.rb_kitchen:
                etSpecifyLocation.setVisibility(View.GONE);
                fault_location = rbKitchen.getText().toString();
                break;
            case R.id.rb_lounge:
                etSpecifyLocation.setVisibility(View.GONE);
                fault_location = rbLounge.getText().toString();
                break;
            case R.id.rb_other:
                etSpecifyLocation.setVisibility(View.VISIBLE);
                fault_location = null;
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
