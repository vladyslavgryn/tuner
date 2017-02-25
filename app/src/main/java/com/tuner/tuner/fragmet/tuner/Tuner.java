package com.tuner.tuner.fragmet.tuner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.tuner.tuner.R;
import com.tuner.tuner.bus.BusProvider;
import com.tuner.tuner.bus.event.FrequencyChangeEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Tuner extends Fragment implements TunerView {

    @BindView(R.id.text_frequency_value)
    TextView textView;

    private TunerPresenter tunerPresenter;
    private Unbinder unbinder;

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tunerPresenter = new TunerPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tuner, container, false);

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tunerPresenter.setText();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        tunerPresenter.removeCallbacks();
        super.onDestroyView();
    }

    @Override
    public void setTextView(String text) {
        textView.setText(text);
    }

    @Subscribe
    public void onPresenceChange(FrequencyChangeEvent event) {
        textView.setText(String.valueOf(event.frequency));
    }
}
