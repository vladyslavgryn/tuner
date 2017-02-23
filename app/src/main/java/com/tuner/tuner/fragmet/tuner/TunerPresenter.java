package com.tuner.tuner.fragmet.tuner;

import android.os.Handler;

import com.tuner.tuner.fragmet.tuner.helper.AudioHelper;

class TunerPresenter {

    private static final int TIME_REPEAT = 2000;

    private AudioHelper audioHelper;
    private TunerView tunerView;
    private Handler handler;
    private Runnable runnable;

    TunerPresenter(TunerView tunerView) {
        this.tunerView = tunerView;
        this.audioHelper = new AudioHelper();
    }

    void setText() {

        audioHelper.startRecording();

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                audioHelper.stopRecording();
            }
        };
        handler.postDelayed(runnable, TIME_REPEAT);
    }

    void removeCallbacks() {
        if (null != handler && null != runnable) {
            handler.removeCallbacks(runnable);
        }
    }
}
