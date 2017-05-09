package com.alobot.ridecellcodingtest.model.interfaces;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface Task_Interface {
    void onGetDirectionsSucess(ArrayList<LatLng> latLngArrayList);

    void onGetDirectionsFail();
}
