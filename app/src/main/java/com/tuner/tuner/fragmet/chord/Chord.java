package com.tuner.tuner.fragmet.chord;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.tuner.tuner.R;
import com.tuner.tuner.adapter.ListViewChordAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Chord extends Fragment implements ChordView {

    @BindView(R.id.list_chords)
    ListView listView;

    @BindView(R.id.image_empty_chords)
    ImageView imageView;

    private Unbinder unbinder;
    private ChordPresenter chordPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chordPresenter = new ChordPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chord, container, false);

        unbinder = ButterKnife.bind(this, view);

        listView.setEmptyView(imageView);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        chordPresenter.getChords();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public String[] getChords() {
        return getResources().getStringArray(R.array.chords);
    }

    @Override
    public void setChords(List<String> chords) {
        ListViewChordAdapter listViewChordAdapter = new ListViewChordAdapter(getContext(), R.layout.list_view_chord, chords);
        listView.setAdapter(listViewChordAdapter);
    }
}
