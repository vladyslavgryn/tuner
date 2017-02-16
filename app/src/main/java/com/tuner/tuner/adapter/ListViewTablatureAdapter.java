package com.tuner.tuner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tuner.tuner.R;

import java.util.List;

public class ListViewTablatureAdapter extends ArrayAdapter<String> {

    private List<String> files;
    private int resource;

    public ListViewTablatureAdapter(Context context, int resource, List<String> files) {
        super(context, resource, files);

        this.resource = resource;
        this.files = files;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        String file = files.get(position);

        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.text_file);

        textView.setText(file);

        return convertView;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }
}
