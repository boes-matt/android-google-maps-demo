package com.example.mapdemo.action;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.example.mapdemo.R;
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
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                showAlertDialog(map, latLng);
            }
        });
    }

    // TODO D2 Show dialog
    // Use AlertDialog.Builder and marker_dialog layout.
    // Set a positive button and a negative button.
    // Positive button should get the title field and add a place to the PlaceManager.
    // Negative button is a no-op.
    // Show the dialog.
    private void showAlertDialog(final GoogleMap map, final LatLng latLng) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Add marker");
        builder.setView(R.layout.marker_dialog);

        final AlertDialog dialog = builder.create();

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText titleField = (EditText) dialog.findViewById(R.id.title);
                String title = titleField != null ?
                        titleField.getText().toString() :
                        "Wow!";
                mPlaceManager.addPlace(map, title, latLng);
            }
        });

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.show();
    }

}
