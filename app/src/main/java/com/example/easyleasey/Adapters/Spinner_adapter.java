package com.example.easyleasey.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.easyleasey.R;

public class Spinner_adapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    String[] typenames;


    public Spinner_adapter(Context context, String[] typenames) {
        this.context = context;
        this.typenames = typenames;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return typenames.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.spinnersetting, null);
        TextView names = (TextView) convertView.findViewById(R.id.spinner_id);
        names.setText(typenames[position]);
        return convertView;
    }

}
