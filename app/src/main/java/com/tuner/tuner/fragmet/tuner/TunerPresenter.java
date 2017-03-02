package com.tuner.tuner.fragmet.tuner;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;

import com.tuner.tuner.R;
import com.tuner.tuner.fragmet.tuner.helper.AudioHelper;

public class TunerPresenter implements Handler.Callback {

    private static final int TIME_REPEAT = 2000;

    private AudioHelper audioHelper;
    private TunerView tunerView;
    public static Handler handler;
    private Runnable runnable;

    TunerPresenter(TunerView tunerView) {
        this.tunerView = tunerView;
        this.audioHelper = new AudioHelper();
    }

    void startRecording() {

        audioHelper.startRecording();

        handler = new Handler(this);

        runnable = new Runnable() {
            @Override
            public void run() {
                if (audioHelper.isRecording()) {
                    audioHelper.stopRecording();
                } else {
                    audioHelper.startRecording();
                }
                handler.postDelayed(runnable, TIME_REPEAT);
            }
        };
        handler.postDelayed(runnable, TIME_REPEAT);
    }

    void requestPermission(int[] grantResults) {
        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startRecording();
        } else {
            tunerView.showMessage(R.string.msg_err_permission, Color.RED);
        }
    }

    void removeCallbacks() {
        if (null != handler && null != runnable) {
            handler.removeCallbacks(runnable);
        }

        if (audioHelper.isRecording()) {
            audioHelper.cancel();
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (AudioHelper.MESSAGE_ID == msg.what) {
            tunerView.setFrequency(msg.arg1);
        }
        return false;
    }
}
