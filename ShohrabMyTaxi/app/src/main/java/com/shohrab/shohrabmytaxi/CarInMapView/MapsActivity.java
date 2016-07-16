package com.shohrab.shohrabmytaxi.CarInMapView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shohrab.shohrabmytaxi.ParcelableModel.CarParcelable;
import com.shohrab.shohrabmytaxi.R;
import com.shohrab.shohrabmytaxi.BroadcastService.BroadCastService;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Bundle mBundle;
    private String carName;
    private double mLat, mLon;
    private ArrayList<CarParcelable> mCarParcelableList;
    private ArrayList<CarParcelable> mCarParcelableListCurrentLation;
    private ArrayList<CarParcelable> mCarParcelableListLastLocation;
    private int mSelectedCar;

    private static final String TAG = "BroadcastTest";
    private Intent mServiceIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // "carList" parcelable object is added to the intent in MainActiVityPresenter class
        mCarParcelableList = getIntent().getParcelableArrayListExtra("carList");

       //"selectedCar" int value is added to the intent in MainActiVityPresenter class
        // this values is used to track down which car is being clicked by the user from then listview
        // in MainActivity class. I move the camera in the map to the selected car
        mSelectedCar = getIntent().getIntExtra("selectedCar",0);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng carLoc = null;

        //Adding markers for all cars to the map
        addMarker();


        //move the camera to the selected car
        carLoc = new LatLng(mCarParcelableList.get(mSelectedCar).mLat,
                mCarParcelableList.get(mSelectedCar).mLon);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(carLoc));

        mMap.animateCamera(CameraUpdateFactory
                .newLatLngZoom(new LatLng(mCarParcelableList.get(mSelectedCar).mLat,
                        mCarParcelableList.get(mSelectedCar).mLon), 16));

        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(carLoc)
                .title(mCarParcelableList.get(mSelectedCar).mCarName)
                .snippet(mCarParcelableList.get(mSelectedCar).mAddress)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));

        marker.showInfoWindow();
        //End of dealing with selected car from previous activity
    }

    //this method is used add markers for each car or update markers of each cars
    // position when their position are changed
    private void addMarker(){

        mMap.clear();

        String lat = Double.toString(mCarParcelableList.get(0).mLat);
        Log.d(TAG, lat);

        LatLng carLoc = null, newCarLoc = null;


        //Initially add markers for all cars to the map
        if(mCarParcelableListCurrentLation==null){
            mCarParcelableListLastLocation = mCarParcelableList;
            for(CarParcelable car : mCarParcelableList) {
                carLoc = new LatLng(car.mLat, car.mLon);

                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(carLoc)
                        .title(car.mCarName)
                        .snippet(car.mAddress)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
            }
        }else{ // then update each car's position by moving the marker smoothly from previous
            // location to the current location
            for(int i=0; i<mCarParcelableListCurrentLation.size();i++) {
                carLoc = new LatLng(mCarParcelableListLastLocation.get(i).mLat, mCarParcelableListLastLocation.get(i).mLon);
                newCarLoc = new LatLng(mCarParcelableListCurrentLation.get(i).mLat, mCarParcelableListCurrentLation.get(i).mLon);

                animateMarker(i, carLoc, newCarLoc, false);
            }

            //set the the last known location of each car to the current location of each car
            //so we will get the updates of car's location then we will move the marker from
            //last known location to the current location again.
            mCarParcelableListLastLocation = mCarParcelableListCurrentLation;
        }

    }


    //creating a broadcast receiver object
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        mServiceIntent = new Intent(this, BroadCastService.class);
        startService(mServiceIntent);//starting the service
        registerReceiver(broadcastReceiver, new IntentFilter(BroadCastService.BROADCAST_ACTION));//register the broadcast receiver
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        stopService(mServiceIntent);
    }

    private void updateUI(Intent intent) {

        //"carList" parcelable object is Broadcast from BroadCastService class
        mCarParcelableListCurrentLation = intent.getParcelableArrayListExtra("carList");

        addMarker();

    }

    //This methos is used to move the marker of each car smoothly when there are any updates of their position
    public void animateMarker(final int position, final LatLng startPosition, final LatLng toPosition,
                              final boolean hideMarker) {


        final Marker marker = mMap.addMarker(new MarkerOptions()
                .position(startPosition)
                .title(mCarParcelableListCurrentLation.get(position).mCarName)
                .snippet(mCarParcelableListCurrentLation.get(position).mAddress)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));


        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();

        final long duration = 1000;
        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startPosition.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startPosition.latitude;

                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }
}
