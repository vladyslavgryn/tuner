package com.tuner.tuner.fragmet.tuner.helper;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.tuner.tuner.fragmet.tuner.TunerView;

import java.util.ArrayList;
import java.util.List;

public class GraphHelper {

    private TunerView tunerView;
    private LineData lineData;
    private LineDataSet lineDataSet;
    private List<Entry> entries = new ArrayList<>();
    private String label;
    private float graphLastValue = 2f;

    public GraphHelper(TunerView tunerView, String label) {
        this.tunerView = tunerView;
        this.label = label;
    }

    public void initLineDataSet() {
        tunerView.setCustomStyle();
        tunerView.setLargeValueFormatter();
        tunerView.disableLegend();

        entries.add(new Entry(0f, 0));
        lineDataSet = new LineDataSet(entries, label);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setLineWidth(3f);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setFillColor(Color.RED);
    }

    public LineData getLineData() {
        lineData = new LineData(lineDataSet);
        return lineData;
    }

    public void updateChart(int y) {
        lineData = tunerView.getChartData();

        if (null != lineData) {
            ILineDataSet set = lineData.getDataSetByIndex(0);

            if (null == set) {
                set = lineDataSet;
                lineData.addDataSet(set);
            }

            lineData.addEntry(getEntry(y), 0);
            lineData.notifyDataChanged();

            tunerView.setChartData(lineData);
        }
    }

    public int getEntryCount() {
        return lineData.getEntryCount();
    }

    public Description createDescription(String desc, float size) {
        Description description = new Description();
        description.setText(desc);
        description.setTextColor(Color.GRAY);
        description.setTextSize(size);

        return description;
    }

    private Entry getEntry(int y) {
        return new Entry(graphLastValue += 2d, y);
    }
}
