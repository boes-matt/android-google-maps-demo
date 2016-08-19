package com.example.mapdemo.action;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mapdemo.helper.OnClient;
import com.example.mapdemo.helper.OnMap;
import com.example.mapdemo.helper.OnPermission;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MoveToLocationFirstTime implements
        OnMap.Listener,
        OnPermission.Listener,
        OnClient.Listener {

    private final Bundle mSavedInstanceState;

    private GoogleApiClient mClient;
    private GoogleMap mGoogleMap;
    private OnPermission.Result mPermissionResult;

    public MoveToLocationFirstTime(@Nullable Bundle savedInstanceState) {
        mSavedInstanceState = savedInstanceState;
    }

    // TODO C2 Move map to current location
    // Use LocationServices' FusedLocationApi.
    // Get last location.
    // Move map with camera.
    // Use LatLng, CameraUpdateFactory, and getCameraPosition helper method.
    //
    // If you see an error, try changing the annotation to @SuppressWarnings("All").
    // We have already checked that the location permission is granted.
    @SuppressWarnings("MissingPermission")
    private void moveToUserLocation(GoogleApiClient client, GoogleMap map) {

    }

    // TODO C3 Build CameraPosition
    // Use CameraPosition.Builder.
    // Set target, zoom, and tilt
    // Play around with zoom and tilt values for 3d effect.
    private CameraPosition getCameraPosition(LatLng latLng) {
        return new CameraPosition.Builder().build();
    }

    private void check() {
        if (mSavedInstanceState == null &&
                mClient != null && mClient.isConnected() &&
                mGoogleMap != null &&
                mPermissionResult == OnPermission.Result.GRANTED) {
            moveToUserLocation(mClient, mGoogleMap);
        }
    }

    @Override
    public void onClient(@Nullable GoogleApiClient client) {
        mClient = client;
        check();
    }

    @Override
    public void onMap(GoogleMap map) {
        mGoogleMap = map;
        check();
    }

    @Override
    public void onResult(int requestCode, OnPermission.Result result) {
        mPermissionResult = result;
        check();
    }

}
