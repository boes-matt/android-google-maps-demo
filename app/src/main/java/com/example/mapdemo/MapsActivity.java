package com.example.mapdemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.maps.android.ui.IconGenerator;

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
            fragment.getMapAsync(new OnMap(manager, click, layer, move, track));
        }

        GoogleApiClient client = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, null)
                .addApi(LocationServices.API).build();
        client.registerConnectionCallbacks(new OnClient(client, move, track));

        int requestCode = 1001;
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        OnPermission.Request location = new OnPermission.Request(requestCode, permission, layer, move, track);
        OnPermission onPermission = new OnPermission.Builder(this).build();
        onPermission.beginRequest(location);
    }

    private IconGenerator getIconGenerator() {
        IconGenerator generator = new IconGenerator(this);
        generator.setStyle(IconGenerator.STYLE_GREEN);
        generator.setTextAppearance(R.style.MarkerFont);
        return generator;
    }

    private LocationRequest getLocationRequest() {
        LocationRequest request = new LocationRequest();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(10000);        // 10 seconds
        request.setFastestInterval(5000);  // 5 seconds
        return request;
    }

}
