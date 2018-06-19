package com.itechhubsa.bimanage;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itechhubsa.bimanage.Pojos.Fault;
import com.itechhubsa.bimanage.Pojos.Message;

import java.util.ArrayList;

public class DirectMessage extends Activity implements View.OnClickListener {
    private ListView list;
    private EditText etMessage;
    private TextView message_description, building_unit;
    private ArrayList<Message> messages;
    private Fault fault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direct_message);
        initialize();
        fault = (Fault) getIntent().getSerializableExtra("fault");
        assert fault != null;
        message_description.setText(fault.getReport_description());
//        building_unit.setText(fault.getUnit_number());
    }

    void initialize() {
        list = findViewById(R.id.single_message_list_view);
        messages = new ArrayList<>();
        etMessage = findViewById(R.id.single_message_et_message);
        message_description = findViewById(R.id.single_message_description);
        building_unit = findViewById(R.id.single_message_unit_number);
        FloatingActionButton fab = findViewById(R.id.fabSend);

        message_description = findViewById(R.id.single_message_description);
        building_unit = findViewById(R.id.single_message_unit_number);
//        fab.setEnabled(false);
        fab.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), Home.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabSend:
                if (!TextUtils.isEmpty(etMessage.getText())) {
                    Message message = new Message(etMessage.getText().toString());
                    messages.add(message);
                    etMessage.setText("");
                    DirectMessageAdapter adapter = new DirectMessageAdapter(this, messages);
                    list.setAdapter(adapter);
                } else {
                    Toast.makeText(getBaseContext(), "Type a message..", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

}
