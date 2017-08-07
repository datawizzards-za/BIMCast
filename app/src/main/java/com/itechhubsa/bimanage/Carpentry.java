package com.itechhubsa.bimanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Carpentry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carpentry);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(),Home.class));
        finish();
    }
}
