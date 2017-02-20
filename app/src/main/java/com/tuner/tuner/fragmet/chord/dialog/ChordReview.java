package com.tuner.tuner.fragmet.chord.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

    public static ChordReview getInstance(String name) {

        ChordReview chordReview = new ChordReview();

        Bundle bundle = new Bundle();

        bundle.putString("chord-name", name);

        chordReview.setArguments(bundle);

        return chordReview;
    }

    @NonNull
    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_chord, null);

        unbinder = ButterKnife.bind(this, view);

        alertDialog.setTitle(getArguments().getString("chord-name"));
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
