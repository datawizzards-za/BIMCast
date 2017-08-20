package com.itechhubsa.bimanage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.itechhubsa.bimanage.Pojos.Message;

import java.text.DateFormat;
import java.util.ArrayList;

public class DirectMessageAdapter extends ArrayAdapter {
    private ArrayList<Message> messages;

    public DirectMessageAdapter(Activity context, ArrayList<Message> messages) {
        super(context, 0, messages);
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Message getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int _id) {
        return _id;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.message_view, parent, false);
        }
        Message message = getItem(position);

        TextView tv_description = (TextView) view.findViewById(R.id.tv_message);
        TextView tv_time = (TextView) view.findViewById(R.id.tv_message_time);
//        TextView tv_
        assert message != null;
        String stringDate = DateFormat.getDateTimeInstance().format(message.getMessageDate());
        tv_description.setText(message.getMessage());
        tv_time.setText(stringDate);

        return view;
    }
}
