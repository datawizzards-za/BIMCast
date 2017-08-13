package com.itechhubsa.bimanage;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.itechhubsa.bimanage.Pojos.Fault;

public class Report extends AppCompatActivity implements View.OnClickListener {
    private String _back_key_pressed;
    private static final int CAMERA_REQUEST = 2017;
    private EditText etDescription, etUnitNo, etParkingSpace;
    private ImageView imgReportProof;
    private String imageUriPath;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        initialize();
        _back_key_pressed = getIntent().getStringExtra("_compare");
    }

    void initialize() {
        Button btnCaptureImage = (Button) findViewById(R.id.imgBtnReport);
        Button btnSubmitReort = (Button) findViewById(R.id.btnSubmitReport);
        //
        etDescription = (EditText) findViewById(R.id.etProblemDesciption);
        etParkingSpace = (EditText) findViewById(R.id.etParkingSpace);
        etUnitNo = (EditText) findViewById(R.id.etUnitNumber);
        //
        imgReportProof = (ImageView) findViewById(R.id.imgReportProof);
        //
        btnCaptureImage.setOnClickListener(this);
        btnSubmitReort.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if(_back_key_pressed.equalsIgnoreCase("Electricity")){
            startActivity(new Intent(getBaseContext(), Electricity.class));
            finish();
        }
        else if(_back_key_pressed.equalsIgnoreCase("Home")){
            startActivity(new Intent(getBaseContext(), Home.class));
            finish();
        }else if(_back_key_pressed.equalsIgnoreCase("Carpentry")){
            startActivity(new Intent(getBaseContext(), Carpentry.class));
            finish();
        }else if(_back_key_pressed.equalsIgnoreCase("Plumbing")){
            startActivity(new Intent(getBaseContext(), Plumbing.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnReport:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
            case R.id.btnSubmitReport:
                if(!TextUtils.isEmpty(etDescription.getText().toString())){
                    FirebaseDatabase.getInstance().getReference().push().setValue(new Fault(etDescription.getText().toString(),
                            Integer.parseInt(etUnitNo.getText().toString()),etParkingSpace.getText().toString(),imageUri.toString()));
                    Toast.makeText(getBaseContext(), "clicked...", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            imgReportProof.setImageURI(imageUri);
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            imgReportProof.setImageBitmap(Bitmap.createScaledBitmap(photo, 320, 480, false));
        }
    }
}
