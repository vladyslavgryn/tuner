package com.tuner.tuner.fragmet.tuner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.tuner.tuner.R;
import com.tuner.tuner.fragmet.tuner.helper.GraphHelper;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Tuner extends Fragment implements TunerView {

//    @BindView(R.id.text_frequency_value)
//    TextView textView;

    @BindView(R.id.tuner_chart)
    LineChart lineChart;

    @BindString(R.string.app_frequency)
    String frequency;

    private TunerPresenter tunerPresenter;
    private GraphHelper graphHelper;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tunerPresenter = new TunerPresenter(this);
        graphHelper = new GraphHelper();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tuner, container, false);

        unbinder = ButterKnife.bind(this, view);
        graphHelper.initLineDataSet(frequency);

        lineChart.setData(graphHelper.getLineData());
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        LargeValueFormatter largeValueFormatter = new LargeValueFormatter();
        largeValueFormatter.setAppendix(frequency);
        lineChart.getAxisLeft().setValueFormatter(largeValueFormatter);

        lineChart.getAxisLeft().setAxisMinimum(0);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tunerPresenter.startRecording();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        tunerPresenter.removeCallbacks();
        super.onDestroyView();
    }

    @Override
    public void setTextView(int value) {
        LineData data = lineChart.getData();

        if (null != data) {
            ILineDataSet set = data.getDataSetByIndex(0);

            if (null == set) {
                set = graphHelper.getLineDataSet();
                data.addDataSet(set);
            }

            data.addEntry(graphHelper.getEntry(value), 0);
            data.notifyDataChanged();

            lineChart.notifyDataSetChanged();
            lineChart.setVisibleXRangeMaximum(120);
            lineChart.moveViewToX(data.getEntryCount());
        }
    }
}
