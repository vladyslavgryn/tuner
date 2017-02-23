package com.tuner.tuner.fragmet.tuner.helper;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

public class AudioHelper {

    private static final int SAMPLE_RATE = 44100;
    private static final int CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNELS, ENCODING);

    private AudioRecord audioRecord;
    private Thread thread;
    private boolean recording;
    private float frequency;

    public void startRecording() {
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNELS, ENCODING, BUFFER_SIZE);
        recording = true;
        audioRecord.startRecording();
        startThread();
    }

    public void stopRecording() {
        audioRecord.stop();
        audioRecord.release();
        thread = null;
        recording = false;
    }

    public boolean isRecordnig() {
        return recording;
    }

    public float getFrequency() {
        return frequency;
    }

    private void startThread() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                frequency = getData();
            }
        }, "AudioRecord");
        thread.start();
    }

    private int getData() {

        short data[] = new short[BUFFER_SIZE];

        while (recording) {
            audioRecord.read(data, 0, BUFFER_SIZE);
        }
        return calculate(data);
    }

    private int calculate(short[] data) {

        int numSamples = data.length;
        int numCrossing = 0;

        for (int i = 0; i < numSamples - 1; i++) {
            if ((data[i] > 0 && data[i+1] <= 0) ||
                    (data[i] < 0 && data[i+1] >= 0)) {
                numCrossing++;
            }
        }

        float numSecondsRecorder = (float) numSamples / (float) SAMPLE_RATE;
        float numCycles = numCrossing / 2;

        return (int) (numCycles / numSecondsRecorder);
    }
}
