package com.itechhubsa.bimanage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.itechhubsa.bimanage.Pojos.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ChartBoard extends AppCompatActivity {

    public static final int SIGN_IN_REQUEST_CODE = 1000;
    private FirebaseListAdapter<Message> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_board);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in/sign up activity
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    SIGN_IN_REQUEST_CODE
            );
        } else {
            // User is already signed in. Therefore, display
            // a welcome Toast
            Toast.makeText(this,
                    "Welcome " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName(),
                    Toast.LENGTH_LONG)
                    .show();

            // Load chat room contents
            displayChatMessages();
        }
        FloatingActionButton fab = findViewById(R.id.fab_send_message);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference("Messages")
                        .push()
                        .setValue(new Message(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );

                // Clear the input
                input.setText("");
            }
        });
    }

    private void displayChatMessages() {
        ListView listOfMessages = findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<Message>(this, Message.class,
                R.layout.message_view, FirebaseDatabase.getInstance().getReference("Messages")) {
            @Override
            protected void populateView(View v, Message model, int position) {

                TextView messageText = v.findViewById(R.id.tv_message);
                TextView messageUser = v.findViewById(R.id.tv_user_name);
                TextView messageTime = v.findViewById(R.id.tv_message_time);

                // Set their text
                messageText.setText(model.getMessage());
                messageUser.setText(model.getUser());

                // Format the date before showing it
                Long date = model.getMessageDate();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy hh:mm");
                String strDate = dateFormat.format(date);

                messageTime.setText(strDate);
            }
        };

        listOfMessages.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this,
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();
                displayChatMessages();
            } else {
                Toast.makeText(this,
                        "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_LONG)
                        .show();
                startActivity(new Intent(getBaseContext(), Home.class));
                // Close the app
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sign_out) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ChartBoard.this,
                                    "You have been signed out.",
                                    Toast.LENGTH_LONG)
                                    .show();
                            startActivity(new Intent(getBaseContext(), Home.class));
                            // Close activity
                            finish();
                        }
                    });
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(),Home.class));
        finish();
    }
}
