package com.tuner.tuner.fragmet.tuner.helper;

import android.graphics.Color;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class GraphHelper {

    private LineData lineData;
    private LineDataSet lineDataSet;
    private float graphLastValue = 2f;
    private List<Entry> entries = new ArrayList<>();

    public GraphHelper() {
        entries.add(new Entry(0, 0));
    }

    public void initLineDataSet(String label) {
        lineDataSet = new LineDataSet(entries, label);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setLineWidth(3f);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setFillColor(Color.RED);
    }

    public LineDataSet getLineDataSet() {
        return lineDataSet;
    }

    public LineData getLineData() {
        lineData = new LineData(lineDataSet);
        return lineData;
    }

    public Entry getEntry(int y) {
        return new Entry(graphLastValue += 2d, y);
    }
}
