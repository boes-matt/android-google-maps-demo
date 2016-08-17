package com.example.mapdemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.maps.android.ui.IconGenerator;

// TODO Test user stories
// - Do you see a map?  Did you add a Google Maps API key?
// - Is the location layer enabled?  Can you center the map at your location?
// - Can you add a custom marker to the map?  Does the marker animate?
// - Can the user input a title for the marker?
// - Do you see location updates in the log?  Filter log with TAG 'MapsActivity'.
// - Does the map center at your location on app open?
// - Do you see 3d buildings on your map?  Try tilting and zooming in.
// - When you rotate your phone, does it NOT crash?

// Presentation slides:
// https://docs.google.com/presentation/d/1MEs_JAyRC3J0YOX0bce1XBG42w5Q6rAvMvHb46yEbpo/edit?usp=sharing

public class MapsActivity extends AppCompatActivity {

    public static final String TAG = MapsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (savedInstanceState == null) {
            Toast.makeText(this, "Long click on map to add marker", Toast.LENGTH_LONG).show();
        }

        AddToMap adder = new AddToMap(getIconGenerator());
        PlaceManager manager = new PlaceManager(adder);
        AddMarkerOnLongClick click = new AddMarkerOnLongClick(this, manager);

        AddLocationLayer layer = new AddLocationLayer();
        MoveToLocationFirstTime move = new MoveToLocationFirstTime(savedInstanceState);
        TrackLocation track = new TrackLocation(getLocationRequest(), new LogLocation());

        new OnActivity.Builder(this, manager, track).build();

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment != null) {
            getMapAsync(fragment, new OnMap(manager, click, layer, move, track));
        }

        GoogleApiClient client = getGoogleApiClient();
        addConnectionCallbacks(client, new OnClient(client, move, track));

        int requestCode = 1001;
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        OnPermission.Request location = new OnPermission.Request(requestCode, permission, layer, move, track);
        OnPermission onPermission = new OnPermission.Builder(this).build();
        onPermission.beginRequest(location);
    }

    // TODO Build IconGenerator
    // Set IconGenerator attributes.
    // Use the MarkerFont text appearance style.
    // Use it to build custom markers.
    private IconGenerator getIconGenerator() {
        return new IconGenerator(this);
    }

    // TODO Build LocationRequest
    // Set priority, interval, and fastest interval.
    // Use it to start location updates.
    private LocationRequest getLocationRequest() {
        return new LocationRequest();
    }

    // TODO Build GoogleApiClient
    // Enable auto manage and add LocationServices API
    private GoogleApiClient getGoogleApiClient() {
        return new GoogleApiClient.Builder(this).build();
    }

    // TODO Get the map asynchronously
    private void getMapAsync(SupportMapFragment fragment, OnMapReadyCallback callback) {

    }

    // TODO Add callbacks to the GoogleApiClient
    private void addConnectionCallbacks(GoogleApiClient client, GoogleApiClient.ConnectionCallbacks callbacks) {

    }

}
