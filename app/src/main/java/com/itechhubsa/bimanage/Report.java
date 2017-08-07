package com.itechhubsa.bimanage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class Report extends AppCompatActivity implements View.OnClickListener {
    private Button btnCsptureImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        initialize();
    }

    void initialize() {
        btnCsptureImage = (Button) findViewById(R.id.imgBtnReport);
        btnCsptureImage.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), Electricity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnReport:
                startActivity(new Intent(getBaseContext(),Capture.class));
                finish();
//                takePicture();
                break;
            default:
                break;
        }
    }
}
