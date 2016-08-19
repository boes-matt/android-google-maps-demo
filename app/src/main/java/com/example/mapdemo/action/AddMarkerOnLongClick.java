package com.example.mapdemo.action;

import android.content.Context;

import com.example.mapdemo.helper.OnMap;
import com.example.mapdemo.helper.PlaceManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class AddMarkerOnLongClick implements OnMap.Listener {

    private final Context mContext;
    private final PlaceManager mPlaceManager;

    public AddMarkerOnLongClick(Context context, PlaceManager manager) {
        mContext = context;
        mPlaceManager = manager;
    }

    // TODO D1 Set long click listener
    // Call showAlertDialog method.
    @Override
    public void onMap(final GoogleMap map) {

    }

    // TODO D2 Show dialog
    // Use AlertDialog.Builder and marker_dialog layout.
    // Set a positive button and a negative button.
    // Positive button should get the title field and add a place to the PlaceManager.
    // Negative button is a no-op.
    // Show the dialog.
    private void showAlertDialog(final GoogleMap map, final LatLng latLng) {

    }

}
