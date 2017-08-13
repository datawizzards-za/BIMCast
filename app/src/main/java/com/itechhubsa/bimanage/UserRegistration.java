package com.itechhubsa.bimanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UserRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);
        initialize();
    }
    void initialize(){

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(),Login.class));
        finish();
    }
}
