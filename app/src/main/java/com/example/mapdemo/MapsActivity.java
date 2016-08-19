package com.example.mapdemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.mapdemo.action.AddLocationLayer;
import com.example.mapdemo.action.AddMarkerOnLongClick;
import com.example.mapdemo.action.AddToMap;
import com.example.mapdemo.action.LogLocation;
import com.example.mapdemo.action.MoveToLocationFirstTime;
import com.example.mapdemo.action.TrackLocation;
import com.example.mapdemo.helper.OnActivity;
import com.example.mapdemo.helper.OnClient;
import com.example.mapdemo.helper.OnMap;
import com.example.mapdemo.helper.OnPermission;
import com.example.mapdemo.helper.PlaceManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.maps.android.ui.IconGenerator;

// TODO A1 Read instructions
//
// Presentation slides:
// https://docs.google.com/presentation/d/1MEs_JAyRC3J0YOX0bce1XBG42w5Q6rAvMvHb46yEbpo/edit?usp=sharing
//
// Summary of TODOs:
// A1 Read instructions
// A2 Prepare Android Studio
// A3 Get Google Maps API key
//
// B1 Build GoogleApiClient
// B2 Get the map asynchronously
// B3 Add callbacks to the GoogleApiClient
//
// C1 Add location layer
// C2 Move map to current location
// C3 Build CameraPosition
//
// D1 Set long click listener
// D2 Show dialog
// D3 Build IconGenerator
// D4 Add marker
// D5 Animate marker
//
// E1 Build LocationRequest
// E2 Request location updates
// E3 Remove location updates
// E4 Log location updates
//
// F1 Test user stories
//
// G1 Understand how OnActivity works
// G2 Understand how OnPermission works
// G3 Understand how PlaceManager works


// TODO A2 Prepare Android Studio
// If Studio complains about major minor version, downgrade gradle in your top-level build.gradle
// and gradle-wrapper.properties.  See files for instructions.
//
// If Studio complains about build tools, go to the SDK manager (icon in toolbar) -> SDK Tools tab
// -> Select Android SDK Build-Tools -> Ok.


// TODO F1 Test user stories
// - Do you see a map?  Did you add a Google Maps API key?
// - Is the location layer enabled?  Can you center the map at your location?
// - Can you add a custom marker to the map?  Does the marker animate?
// - Can the user input a title for the marker?
// - Do you see location updates in the log?  Filter log with TAG 'MapsActivity'.
// - Does the map center at your location on app open?
// - Do you see 3d buildings on your map?  Try tilting and zooming in.
// - When you rotate your phone, does it NOT crash?


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

    // TODO D3 Build IconGenerator
    // Set IconGenerator attributes.
    // Use the MarkerFont text appearance style.
    // Use it to build custom markers.
    private IconGenerator getIconGenerator() {
        return new IconGenerator(this);
    }

    // TODO E1 Build LocationRequest
    // Set priority, interval, and fastest interval.
    // Use it to start location updates.
    private LocationRequest getLocationRequest() {
        return new LocationRequest();
    }

    // TODO B1 Build GoogleApiClient
    // Enable auto manage and add LocationServices API
    private GoogleApiClient getGoogleApiClient() {
        return new GoogleApiClient.Builder(this).build();
    }

    // TODO B2 Get the map asynchronously
    private void getMapAsync(SupportMapFragment fragment, OnMapReadyCallback callback) {

    }

    // TODO B3 Add callbacks to the GoogleApiClient
    private void addConnectionCallbacks(GoogleApiClient client, GoogleApiClient.ConnectionCallbacks callbacks) {

    }

}
