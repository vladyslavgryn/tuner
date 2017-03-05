package com.tuner.tuner.fragmet.chord.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.tuner.tuner.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChordReview extends DialogFragment {

    @BindView(R.id.image_chord)
    ImageView imageView;

    private Unbinder unbinder;
    private String name;

    public static ChordReview getInstance(String name) {

        ChordReview chordReview = new ChordReview();

        Bundle bundle = new Bundle();

        bundle.putString("chord-name", name);

        chordReview.setArguments(bundle);

        return chordReview;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = getArguments().getString("chord-name");
    }

    @NonNull
    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_chord, null);

        unbinder = ButterKnife.bind(this, view);

        String newName = name.toLowerCase();

        if (newName.length() > 1) {
            newName = newName.substring(0, newName.length() - 1) + "h";
        }

        int resourceId = getResources().getIdentifier("ic_chord_" + newName, "drawable", getContext().getPackageName());

        if (resourceId != 0) {
            imageView.setImageResource(resourceId);
        }

        alertDialog.setTitle(name);
        alertDialog.setNegativeButton(R.string.btn_close, null);
        alertDialog.setView(view);

        return alertDialog.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
