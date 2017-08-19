package com.itechhubsa.bimanage;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.itechhubsa.bimanage.Pojos.Message;

import java.util.ArrayList;

public class DirectMessageAdapter extends ArrayAdapter {
    private ArrayList<Message> messages = new ArrayList<>();

    public DirectMessageAdapter(Context context, int resource, ArrayList<Message> messages) {
        super(context, resource, messages);

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
