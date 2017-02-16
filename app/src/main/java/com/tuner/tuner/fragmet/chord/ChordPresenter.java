package com.tuner.tuner.fragmet.chord;

import java.util.Arrays;

class ChordPresenter {

    private ChordView chordView;

    ChordPresenter(ChordView chordView) {
        this.chordView = chordView;
    }

    void getChords() {
        String[] chords = chordView.getChords();
        chordView.setChords(Arrays.asList(chords));
    }
}
