package com.tuner.tuner.fragmet.tuner;

import com.github.mikephil.charting.data.LineData;

public interface TunerView {

    void setFrequency(int value);

    void disableLegend();

    void setCustomStyle();

    void setLargeValueFormatter();

    LineData getChartData();

    void setChartData(LineData lineData);
}
