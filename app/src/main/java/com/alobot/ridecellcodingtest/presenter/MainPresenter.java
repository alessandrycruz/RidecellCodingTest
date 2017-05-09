package com.alobot.ridecellcodingtest.presenter;

import android.widget.Toast;

import com.alobot.ridecellcodingtest.model.MainModel;
import com.alobot.ridecellcodingtest.model.interfaces.ActivityLifecycle_Interface;
import com.alobot.ridecellcodingtest.model.interfaces.View_Interface;
import com.alobot.ridecellcodingtest.model.utils.Base_Util;
import com.alobot.ridecellcodingtest.view.MainActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class MainPresenter implements ActivityLifecycle_Interface {
    private Base_Util mBase_Util;
    private View_Interface mView_Interface;
    private MainModel mMainModel;

    public MainPresenter(View_Interface view_Interface) {
        mBase_Util = new Base_Util();
        mView_Interface = view_Interface;
        mMainModel = new MainModel();
    }

    @Override
    public void onCreate() {
        mBase_Util = new Base_Util();
        mMainModel = new MainModel();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroy() {
    }

    public void onMapReady(final GoogleMap googleMap) {
    }

    public void onStartNavigation(MainActivity mainActivity, GoogleMap googleMap, String speed, LatLng[] latLng) {
        if (!speed.isEmpty()) {
            if (latLng != null && latLng.length == 2) {
                if (latLng[0] != null && latLng[1] != null) {
                    mMainModel.findDirections(mainActivity, speed, googleMap, latLng[0].latitude, latLng[0].longitude,
                            latLng[1].latitude, latLng[1].longitude,
                            Base_Util.MODE_DRIVING);
                } else {
                    Toast.makeText(mainActivity, "Long Tap two valid points in the map.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(mainActivity, "Speed cannot be empty.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClearView() {
        mView_Interface.onClearView();

        // mMainModel.restart();
    }
}