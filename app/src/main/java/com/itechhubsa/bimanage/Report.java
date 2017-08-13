package com.itechhubsa.bimanage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Report extends AppCompatActivity implements View.OnClickListener {
    private String _back_key_pressed;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imgReportProof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        initialize();
        _back_key_pressed = getIntent().getStringExtra("_compare");
    }

    void initialize() {
        Button btnCsptureImage = (Button) findViewById(R.id.imgBtnReport);
        imgReportProof = (ImageView) findViewById(R.id.imgReportProof);
        btnCsptureImage.setOnClickListener(this);
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
            default:
                break;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgReportProof.setImageBitmap(Bitmap.createScaledBitmap(photo, 320, 480, false));
        }
    }
}
