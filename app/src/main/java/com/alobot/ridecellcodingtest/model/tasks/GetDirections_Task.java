package com.alobot.ridecellcodingtest.model.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.alobot.ridecellcodingtest.model.interfaces.Task_Interface;
import com.alobot.ridecellcodingtest.model.utils.Base_Util;
import com.alobot.ridecellcodingtest.view.MainActivity;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Map;

public class GetDirections_Task extends AsyncTask<Map<String, String>, Object, ArrayList> {
    public static final String USER_CURRENT_LAT = "user_current_lat";
    public static final String USER_CURRENT_LONG = "user_current_long";
    public static final String DESTINATION_LAT = "destination_lat";
    public static final String DESTINATION_LONG = "destination_long";
    public static final String DIRECTIONS_MODE = "directions_mode";

    private MainActivity activity;
    private Task_Interface task_Interface;
    private Exception exception;
    private ProgressDialog progressDialog;

    public GetDirections_Task(MainActivity activity, Task_Interface task_Interface) {
        this.activity = activity;
        this.task_Interface = task_Interface;
    }

    public void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Calculating directions");
        progressDialog.show();
    }

    @Override
    protected ArrayList doInBackground(Map<String, String>... params) {
        Map<String, String> paramMap = params[0];

        try {
            LatLng fromPosition = new LatLng(Double.valueOf(paramMap.get(USER_CURRENT_LAT)),
                    Double.valueOf(paramMap.get(USER_CURRENT_LONG)));
            LatLng toPosition = new LatLng(Double.valueOf(paramMap.get(DESTINATION_LAT)),
                    Double.valueOf(paramMap.get(DESTINATION_LONG)));
            Base_Util md = new Base_Util();
            Document doc = md.getDocument(fromPosition, toPosition, paramMap.get(DIRECTIONS_MODE));
            ArrayList directionPoints = md.getDirection(doc);

            return directionPoints;
        } catch (Exception e) {
            exception = e;

            return null;
        }
    }

    @Override
    public void onPostExecute(ArrayList result) {
        progressDialog.dismiss();

        if (exception == null) {
            task_Interface.onGetDirectionsSucess(result);
        } else {
            task_Interface.onGetDirectionsFail();
        }
    }
}