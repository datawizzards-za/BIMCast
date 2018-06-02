package com.itechhubsa.bimanage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText etPassword, etEmail;
    private Button btnLogin;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initialize();
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
//                    Toast.makeText(getBaseContext(), "onAuthStateChanged:signed_in:"
//                            + user.getUid(), Toast.LENGTH_SHORT).show()
                    Toast.makeText(getBaseContext(),"You have been logged in",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(),Home.class));
                    finish();
                } else {
                    // User is signed out
                    Toast.makeText(getBaseContext(), "onAuthStateChanged:signed_out", Toast.LENGTH_SHORT).show();
                }
            }
        };
        // [END auth_state_listener]
    }

    void initialize() {
        btnLogin = findViewById(R.id.login_Button);
        etEmail = findViewById(R.id.etLogin_Email);
        etPassword = findViewById(R.id.etLogin_Password);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_Button:
                signIn(etEmail.getText().toString(), etPassword.getText().toString());
                break;
            default:
                break;
        }
    }

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getBaseContext(), R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(getBaseContext(), Home.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private boolean validateForm() {

        String email = etEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Required.");
            return false;
        } else {
            etEmail.setError(null);
        }

        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Required.");
            return false;
        } else {
            etPassword.setError(null);
        }

        return true;
    }

    // [START on_start_add_listener]
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    //[END on_stop_remove_listener]
}