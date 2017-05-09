package com.alobot.ridecellcodingtest.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alobot.ridecellcodingtest.R;
import com.alobot.ridecellcodingtest.model.interfaces.View_Interface;
import com.alobot.ridecellcodingtest.presenter.MainPresenter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements View_Interface, OnMapReadyCallback {
    private static final int MAX_NUMBER_OF_MARKERS = 2;

    private int mMarkerCounter;
    private Button mButton_Start;
    private EditText mEditTextSpeed;
    private GoogleMap mGoogleMap;
    private LatLng[] mLatLngRoute;
    private MainPresenter mMainPresenter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_reset:
                mMainPresenter.onClearView();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mMainPresenter = new MainPresenter(this);
        mEditTextSpeed = (EditText) findViewById(R.id.edit_text_speed);
        mButton_Start = (Button) findViewById(R.id.button_start);
        mButton_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPresenter.onStartNavigation(MainActivity.this, mGoogleMap,
                        mEditTextSpeed.getText().toString(), mLatLngRoute);
            }
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mMainPresenter.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mMainPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mMainPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mMainPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mMainPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMainPresenter.onDestroy();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mLatLngRoute = new LatLng[MAX_NUMBER_OF_MARKERS];

        mGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (mMarkerCounter < MAX_NUMBER_OF_MARKERS) {
                    mGoogleMap.addMarker(new MarkerOptions().position(latLng)).showInfoWindow();
                    mLatLngRoute[mMarkerCounter] = latLng;

                    mMarkerCounter++;
                } else {
                    Toast.makeText(MainActivity.this, "Route already selected.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClearView() {
        mGoogleMap.clear();

        mEditTextSpeed.setText("");

        mMarkerCounter = 0;
    }
}