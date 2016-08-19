package com.example.mapdemo.action;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.ui.IconGenerator;

public class AddToMap {

    private IconGenerator mIconGenerator;

    public AddToMap(IconGenerator generator) {
        mIconGenerator = generator;
    }

    // TODO D4 Add marker
    // Use IconGenerator, MarkerOptions, BitmapDescriptorFactory, and GoogleMap.
    // Call animate method if animate flag is true.
    public void addTo(GoogleMap map, String title, LatLng latLng, boolean animate) {

    }

    // TODO D5 Animate marker
    // Get the marker's position and the map's projection.
    // Create start and stop LatLng's to animate with.
    // Create a ValueAnimator and add an update listener.
    // In the update listener, use SphericalUtil to calculate interpolated
    // LatLng and set the marker's position to this LatLng.
    // Set the animator's interpolator to any subclass of TimeInterpolator.
    // Set the animator's duration.
    // Start the animator.
    private void animate(GoogleMap map, final Marker marker) {

    }

}
