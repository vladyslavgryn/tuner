package com.tuner.tuner.fragmet.chord;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.tuner.tuner.R;
import com.tuner.tuner.fragmet.chord.adapter.ListViewChordAdapter;
import com.tuner.tuner.fragmet.chord.dialog.ChordReview;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Chord extends Fragment implements ChordView, AdapterView.OnItemClickListener {

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
        listView.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = (String) parent.getItemAtPosition(position);

        FragmentManager fragmentManager = getFragmentManager();
        ChordReview chordReview = ChordReview.getInstance(name);
        chordReview.show(fragmentManager, "chord");
    }
}
