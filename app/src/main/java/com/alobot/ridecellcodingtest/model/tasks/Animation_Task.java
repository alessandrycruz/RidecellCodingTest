package com.alobot.ridecellcodingtest.model.tasks;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Animation_Task extends AsyncTask<Void, Integer, Void> {
    private GoogleMap googleMap;
    private ArrayList<LatLng> latLngArrayList;
    private long speed;
    private CameraUpdate center;
    private CameraUpdate zoom;

    public Animation_Task(ArrayList<LatLng> latLngArrayList, GoogleMap googleMap, long speed) {
        this.latLngArrayList = latLngArrayList;
        this.googleMap = googleMap;
        this.speed = speed;
    }

    @Override
    protected Void doInBackground(Void... params) {
        for (int i = 0; i < latLngArrayList.size(); i++) {
            center = CameraUpdateFactory.newLatLng(latLngArrayList.get(i));
            zoom = CameraUpdateFactory.zoomTo(15);

            try {
                Thread.sleep(speed);
            } catch (Exception e) {
                e.printStackTrace();
            }

            publishProgress((int) ((i / (float) latLngArrayList.size()) * 100));
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
    }
}
