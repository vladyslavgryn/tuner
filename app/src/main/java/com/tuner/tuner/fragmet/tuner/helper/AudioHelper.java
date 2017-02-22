package com.tuner.tuner.fragmet.tuner.helper;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

public class AudioHelper {

    private static final int SAMPLE_RATE = 44100;
    private static final int CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNELS, ENCODING);

    private AudioRecord audioRecord;
    private Thread thread;
    private boolean recording;

    public void startRecording() {
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNELS, ENCODING, BUFFER_SIZE * 2);
        recording = true;
        audioRecord.startRecording();
        startThread();
    }

    public void stopRecording() {
        Log.d("stop", "stop");
        audioRecord.stop();
        audioRecord.release();
        thread = null;
        recording = false;
    }

    public boolean isRecordnig() {
        return recording;
    }

    private void startThread() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, "AudioRecord");
        thread.start();
    }

    private void getData() {

        short data[] = new short[BUFFER_SIZE];
        double[] toTransform = new double[BUFFER_SIZE];

        while (recording) {
            int bufferReadResult = audioRecord.read(data, 0, BUFFER_SIZE);

            for(int i = 0; i < BUFFER_SIZE && i < bufferReadResult; i++) {
                toTransform[i] = (double) data[i] / 32768.0; // signed 16 bit
            }
        }
    }
}
