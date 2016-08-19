package com.example.mapdemo.action;

import com.example.mapdemo.helper.OnMap;
import com.example.mapdemo.helper.OnPermission;
import com.google.android.gms.maps.GoogleMap;

public class AddLocationLayer implements
        OnMap.Listener,
        OnPermission.Listener {

    private GoogleMap mGoogleMap;
    private OnPermission.Result mPermissionResult;

    // TODO C1 Add location layer
    // If you see an error, try changing the annotation to @SuppressWarnings("All").
    // We have already checked that the location permission is granted.
    @SuppressWarnings("MissingPermission")
    private void addLayer(GoogleMap map) {

    }

    private void check() {
        if (mGoogleMap != null &&
                mPermissionResult == OnPermission.Result.GRANTED) {
            addLayer(mGoogleMap);
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
