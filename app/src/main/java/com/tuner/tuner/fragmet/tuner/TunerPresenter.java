package com.tuner.tuner.fragmet.tuner;

import android.os.Handler;
import android.util.Log;

import java.util.Random;

class TunerPresenter {

    private TunerView tunerView;
    private Handler handler;
    private Runnable runnable;

    TunerPresenter(TunerView tunerView) {
        this.tunerView = tunerView;
    }

    void setText() {
        final Random random = new Random();

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("run", "rub");
                tunerView.setTextView(String.valueOf(random.nextInt(200)));
                handler.postDelayed(this, 2000);
            }
        };

        handler.postDelayed(runnable, 2000);
    }

    void removeCallbacks() {
        if (null != handler && null != runnable) {
            handler.removeCallbacks(runnable);
        }
    }
}
