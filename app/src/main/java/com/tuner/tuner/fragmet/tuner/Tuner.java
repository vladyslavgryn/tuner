package com.tuner.tuner.fragmet.tuner;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.tuner.tuner.R;
import com.tuner.tuner.fragmet.tuner.helper.GraphHelper;
import com.tuner.tuner.helper.Permission;
import com.tuner.tuner.helper.PermissionInterface;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Tuner extends Fragment implements TunerView, OnChartValueSelectedListener, PermissionInterface {

    public static final String[] AUDIO_RECORDER = new String[]{ Manifest.permission.RECORD_AUDIO };
    public static final int REQUEST_AUDIO = 1;

    @BindView(R.id.relative_layout)
    RelativeLayout relativeLayout;

    @BindView(R.id.tuner_chart)
    LineChart lineChart;

    @BindView(R.id.text_frequency_value)
    TextView textFrequency;

    @BindView(R.id.text_frequency_chord)
    TextView getTextFrequencyChord;

    @BindString(R.string.app_frequency_unit)
    String frequencyUnit;

    @BindString(R.string.app_frequency)
    String frequency;

    private TunerPresenter tunerPresenter;
    private GraphHelper graphHelper;
    private Permission permission;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tunerPresenter = new TunerPresenter(this);
        permission = new Permission(getContext());
        permission.setPermissionInterface(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tuner, container, false);

        unbinder = ButterKnife.bind(this, view);
        graphHelper = new GraphHelper(this, frequencyUnit);
        graphHelper.initLineDataSet();

        lineChart.setData(graphHelper.getLineData());
        lineChart.setOnChartValueSelectedListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        permission.checkPermission(Manifest.permission.RECORD_AUDIO, getActivity());
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        tunerPresenter.removeCallbacks();
        super.onDestroyView();
    }

    @Override
    public void setFrequency(int value) {
        graphHelper.updateChart(value);
        textFrequency.setText(String.valueOf(value) + " " + frequencyUnit);
    }

    @Override
    public void disableLegend() {
        lineChart.getLegend().setEnabled(false);
    }

    @Override
    public void setCustomStyle() {
        lineChart.setDescription(graphHelper.createDescription(frequency, 13f));
        lineChart.getXAxis().setEnabled(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setAxisMinimum(0f);
        lineChart.getAxisLeft().setGranularity(1f);
        lineChart.getAxisLeft().setTextSize(11f);
        lineChart.getAxisLeft().setDrawAxisLine(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);
    }

    @Override
    public void setLargeValueFormatter() {
        LargeValueFormatter largeValueFormatter = new LargeValueFormatter();
        largeValueFormatter.setAppendix(frequencyUnit);
        lineChart.getAxisLeft().setValueFormatter(largeValueFormatter);
    }

    @Override
    public void setVisibilityChart(int visibility) {
        lineChart.setVisibility(visibility);
    }

    @Override
    public LineData getChartData() {
        return lineChart.getData();
    }

    @Override
    public void setChartData(LineData lineData) {
        lineChart.notifyDataSetChanged();
        lineChart.setVisibleXRangeMaximum(40);
        lineChart.moveViewToX(graphHelper.getEntryCount());
        lineChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(getContext(), frequency + ": " + e.getY() + frequencyUnit, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void requestPermission() {
        requestPermissions(AUDIO_RECORDER, REQUEST_AUDIO);
    }

    @Override
    public void requestPermissionRationale() {
        Snackbar.make(relativeLayout, R.string.app_ok, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.app_permission_audio, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestPermissions(AUDIO_RECORDER, REQUEST_AUDIO);
                    }
                }).show();
    }

    @Override
    public void permissionGranted() {
        tunerPresenter.startRecording();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_AUDIO) {
            tunerPresenter.requestPermission(grantResults);
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void showMessage(int resId, int colorId) {
        Snackbar snackbar = Snackbar.make(relativeLayout, resId, Snackbar.LENGTH_LONG);
        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(colorId);
        snackbar.show();
    }
}
