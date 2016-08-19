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
// You will create an app that displays the user's location on the map,
// allows the user to add markers to the map, and receives regular updates
// on the user's location.  The task is to read and complete each of the
// TODOs starting with this one.
//
// PRESENTATION SLIDES:
// https://docs.google.com/presentation/d/1MEs_JAyRC3J0YOX0bce1XBG42w5Q6rAvMvHb46yEbpo/edit?usp=sharing
//
// HELPFUL LINKS:
// Setup troubleshooting: http://guides.codepath.com/android/Google-Maps-Fragment-Guide#troubleshooting
// Completed lab: https://github.com/boes-matt/android-google-maps-demo/tree/completed
// Maps guide: http://guides.codepath.com/android/Google-Maps-API-v2-Usage
// Google Play Services code reference: https://developers.google.com/android/reference/packages
// Android code reference: https://developer.android.com/reference/packages.html
//
// RUNNING THE APP:
// My advice is to use an actual device or the official Android emulator:
// http://guides.codepath.com/android/Google-Maps-Fragment-Guide#setup-x86-based-emulator
//
// But if using the Genymotion emulator, setup Google Play Services and GPS:
// http://guides.codepath.com/android/Genymotion-2.0-Emulators-with-Google-Play-support#setup-google-play-services
//
// SUMMARY OF TODOs:
// I suggest you compile and run the project after completing each section.
//
// A1 Read instructions (MapsActivity)
// A2 Prepare Android Studio (MapsActivity)
// A3 Get Google Maps API key (AndroidManifest)
// A4 Check out the project's library dependencies (app/build.gradle)
//
// B1 Build GoogleApiClient (MapsActivity)
// B2 Get the map asynchronously (MapsActivity)
// B3 Register callbacks on the GoogleApiClient (MapsActivity)
//
// C1 Enable location layer (AddLocationLayer)
// C2 Move map to current location (MoveToLocationFirstTime)
// C3 Build CameraPosition (MoveToLocationFirstTime)
//
// D1 Set long click listener (AddMarkerOnLongClick)
// D2 Show dialog (AddMarkerOnLongClick)
// D3 Build IconGenerator (MapsActivity)
// D4 Add marker (AddToMap)
// D5 Animate marker (AddToMap)
//
// E1 Build LocationRequest (MapsActivity)
// E2 Request location updates (TrackLocation)
// E3 Remove location updates (TrackLocation)
// E4 Log location updates (LogLocation)
//
// F1 Test user stories (MapsActivity)
//
// G1 Understand how OnActivity works (OnActivity)
// G2 Understand how OnPermission works (OnPermission)
// G3 Understand how PlaceManager works (PlaceManager)


// TODO A2 Prepare Android Studio
// If Studio complains about major minor version, downgrade gradle in your top-level build.gradle
// and gradle-wrapper.properties.  See files for instructions.
//
// If Studio complains about build tools or support repository, go to the SDK manager (icon in toolbar)
// -> SDK Tools tab -> Select Android SDK Build-Tools or Support Repository -> Ok.


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
    // IconGenerator is for your custom markers.
    // It is part of the Maps utility library.
    // Set a style and a text appearance on the IconGenerator.
    // Use R.style.MarkerFont for the text appearance.
    // Check it out in res/values/styles.xml
    private IconGenerator getIconGenerator() {
        return new IconGenerator(this);
    }

    // TODO E1 Build LocationRequest
    // This LocationRequest is used in TrackLocation
    // to start location updates.
    // Set a priority, interval, and fastest interval.
    // Look up what these settings mean in the Google Play
    // Services code reference (See A1 under HELPFUL LINKS).
    private LocationRequest getLocationRequest() {
        return new LocationRequest();
    }

    // TODO B1 Build GoogleApiClient
    // Use the GoogleApiClient.Builder to enable auto manage
    // and add the LocationServices API.
    // See the official GoogleApiClient guide:
    // https://developers.google.com/android/guides/api-client
    // Auto manage connects to, handles errors, and disconnects from
    // Google Play Services, well, automatically.
    // The required OnConnectionFailedListener is only notified
    // for unrecoverable errors.  You can pass null here if you
    // want to do nothing in that situation.
    private GoogleApiClient getGoogleApiClient() {
        return new GoogleApiClient.Builder(this).build();
    }

    // TODO B2 Get the map asynchronously
    private void getMapAsync(SupportMapFragment fragment, OnMapReadyCallback callback) {

    }

    // TODO B3 Register callbacks on the GoogleApiClient
    private void addConnectionCallbacks(GoogleApiClient client, GoogleApiClient.ConnectionCallbacks callbacks) {

    }

}
