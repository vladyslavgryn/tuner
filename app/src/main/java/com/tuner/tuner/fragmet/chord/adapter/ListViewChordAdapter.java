package com.tuner.tuner.fragmet.chord.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tuner.tuner.R;

import java.util.List;

public class ListViewChordAdapter extends ArrayAdapter<String> {

    private List<String> chords;
    private int resource;

    public ListViewChordAdapter(Context context, int resource, List<String> chords) {
        super(context, resource, chords);

        this.resource = resource;
        this.chords = chords;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        String file = chords.get(position);

        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.text_chord);

        textView.setText(file);

        return convertView;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }
}
