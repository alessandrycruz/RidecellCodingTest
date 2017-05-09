package com.alobot.ridecellcodingtest.model;

import android.graphics.Color;
import android.widget.EditText;
import android.widget.Toast;

import com.alobot.ridecellcodingtest.model.interfaces.Task_Interface;
import com.alobot.ridecellcodingtest.model.tasks.Animation_Task;
import com.alobot.ridecellcodingtest.model.tasks.GetDirections_Task;
import com.alobot.ridecellcodingtest.view.MainActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainModel {
    private static final int POLYLINE_WIDTH = 5;
    private static final float CAMERA_ZOOM = 20f;

    public void findDirections(final MainActivity mainActivity, final String speed, final GoogleMap googleMap, double fromPositionDoubleLat,
                               double fromPositionDoubleLong, double toPositionDoubleLat,
                               double toPositionDoubleLong, String mode) {
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put(GetDirections_Task.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
        stringStringMap.put(GetDirections_Task.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
        stringStringMap.put(GetDirections_Task.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
        stringStringMap.put(GetDirections_Task.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
        stringStringMap.put(GetDirections_Task.DIRECTIONS_MODE, mode);

        GetDirections_Task asyncTask = new GetDirections_Task(mainActivity, new Task_Interface() {
            @Override
            public void onGetDirectionsSucess(ArrayList<LatLng> latLngArrayList) {
                handleGetDirectionsResult(latLngArrayList, googleMap, speed);
            }

            @Override
            public void onGetDirectionsFail() {
                processException(mainActivity);
            }
        });
        asyncTask.execute(stringStringMap);
    }

    public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints, GoogleMap googleMap, String speed) {
        PolylineOptions rectLine = new PolylineOptions().width(POLYLINE_WIDTH).color(Color.BLUE);

        for (int i = 0; i < directionPoints.size(); i++) {
            rectLine.add(directionPoints.get(i));
        }

        Polyline newPolyline = googleMap.addPolyline(rectLine);

        for (int i = 0; i < directionPoints.size(); i++) {
            CameraUpdate center =
                    CameraUpdateFactory.newLatLng(directionPoints.get(i));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(CAMERA_ZOOM);

            googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);
        }

        new Animation_Task(directionPoints, googleMap,
                Long.parseLong(speed)).execute();
    }

    private void processException(MainActivity mainActivity) {
        Toast.makeText(mainActivity, "Error to get data.", Toast.LENGTH_SHORT).show();
    }
}
