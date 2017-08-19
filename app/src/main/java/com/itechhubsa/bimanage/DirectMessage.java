package com.itechhubsa.bimanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.itechhubsa.bimanage.Pojos.Message;

import java.util.ArrayList;

public class DirectMessage extends AppCompatActivity {
    private ListView list;
    private TextView message_description, building_unit;
    private ArrayList<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direct_message);
        initilize();
//        DirectMessageAdapter adapter = new DirectMessageAdapter(messages);
    }
    void initilize(){
        list = (ListView) findViewById(R.id.single_message_list_view);
        messages = new ArrayList<>();
        message_description = (TextView) findViewById(R.id.single_message_description);
        building_unit = (TextView) findViewById(R.id.single_message_unit_number);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(),Home.class));
        finish();
    }
}
