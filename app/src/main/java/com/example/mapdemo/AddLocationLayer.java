package com.example.mapdemo;

import com.google.android.gms.maps.GoogleMap;

public class AddLocationLayer implements
        OnMap.Listener,
        OnPermission.Listener {

    private GoogleMap mGoogleMap;
    private OnPermission.Result mPermissionResult;

    @SuppressWarnings("MissingPermission")
    private void addLayer() {
        mGoogleMap.setMyLocationEnabled(true);
    }

    private void check() {
        if (mGoogleMap != null &&
                mPermissionResult == OnPermission.Result.GRANTED) {
            addLayer();
        }
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
