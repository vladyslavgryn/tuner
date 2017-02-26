package com.tuner.tuner.fragmet.tuner.helper;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphHelper {

    private LineGraphSeries<DataPoint> series;
    private double graphLastValue = 0d;

    public GraphHelper() {
        series = new LineGraphSeries<>();
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(2);
    }

    public LineGraphSeries<DataPoint> getSeries() {
        return series;
    }

    public DataPoint addData(int value) {

        return new DataPoint(graphLastValue += 2d, value);
    }
}
