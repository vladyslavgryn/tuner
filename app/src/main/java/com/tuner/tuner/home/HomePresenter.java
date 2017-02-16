package com.tuner.tuner.home;

import android.view.MenuItem;

import com.tuner.tuner.R;
import com.tuner.tuner.fragmet.chord.Chord;
import com.tuner.tuner.fragmet.tablature.Tablature;
import com.tuner.tuner.fragmet.tuner.Tuner;

class HomePresenter {

    private HomeView homeView;

    HomePresenter(HomeView homeView) {
        this.homeView = homeView;
    }

    void onItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_tablature:
                setFragment(R.string.app_tablature, Tablature.class);
                break;
            case R.id.menu_chord:
                setFragment(R.string.app_chord, Chord.class);
                break;
            case R.id.menu_tuner:
                setFragment(R.string.app_tuner, Tuner.class);
                break;
        }
    }

    void setFragment(int resId, Class aClass) {
        homeView.setSubTitleToolbar(resId);
        homeView.showFragment(aClass);
    }
}
