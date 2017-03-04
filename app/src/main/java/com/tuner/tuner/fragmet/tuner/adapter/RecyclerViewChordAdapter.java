package com.tuner.tuner.fragmet.tuner.adapter;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuner.tuner.R;

import java.util.List;

public class RecyclerViewChordAdapter extends RecyclerView.Adapter<RecyclerViewChordAdapter.ViewHolder> {

    private List<String> chords;

    public RecyclerViewChordAdapter(List<String> chords) {
        this.chords = chords;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_chord, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String chord= chords.get(position);

        holder.textChord.setText(fromHtml(chord));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return chords.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textChord;

        ViewHolder(View view) {
            super(view);

            textChord = (TextView) view.findViewById(R.id.text_chord);
        }
    }

    @SuppressWarnings("deprecation")
    private Spanned fromHtml(String source) {
        Spanned spanned;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            spanned = Html.fromHtml(source);
        }

        return spanned;
    }
}
