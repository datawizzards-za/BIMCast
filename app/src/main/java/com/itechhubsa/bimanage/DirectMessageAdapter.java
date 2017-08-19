package com.itechhubsa.bimanage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.itechhubsa.bimanage.Pojos.Message;

import java.util.ArrayList;

public class DirectMessageAdapter extends BaseAdapter {
    private ArrayList<Message> messages = new ArrayList<>();
    DirectMessageAdapter(ArrayList<Message> messages){
        this.messages = messages;
    }
    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int _id) {
        return _id;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {


        return view;
    }
}
