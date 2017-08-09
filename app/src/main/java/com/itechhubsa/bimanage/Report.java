package com.itechhubsa.bimanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Report extends AppCompatActivity implements View.OnClickListener {
    private String _back_key_pressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        initialize();
        _back_key_pressed = getIntent().getStringExtra("_compare");
    }

    void initialize() {
        Button btnCsptureImage = (Button) findViewById(R.id.imgBtnReport);
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
                startActivity(new Intent(getBaseContext(),Capture.class));
                finish();
                break;
            default:
                break;
        }
    }
}
