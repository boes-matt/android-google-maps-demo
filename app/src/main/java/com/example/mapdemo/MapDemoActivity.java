package com.example.mapdemo;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MapDemoActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MapDemoActivity.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_demo_activity);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(LocationServices.API).build();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            setupMap(mapFragment);
        } else {
            Log.e(TAG, "MagFragment is null.  Is it in your layout?");
        }
	}

    private void setupMap(@NonNull SupportMapFragment mapFragment) {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                MapDemoActivityPermissionsDispatcher
                        .centerMapAtCurrentLocationWithCheck(MapDemoActivity.this, googleMap);
                // Do other stuff
            }
        });
    }

    @SuppressWarnings("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    protected void centerMapAtCurrentLocation(@NonNull final GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(true);

        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                mGoogleApiClient.unregisterConnectionCallbacks(this);
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                        getLocationRequest(), getLocationListener(googleMap));
            }

            @Override
            public void onConnectionSuspended(int i) {
                mGoogleApiClient.unregisterConnectionCallbacks(this);
            }
        });
    }

    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(60000);          // 60 seconds
        locationRequest.setFastestInterval(5000);    //  5 seconds
        return locationRequest;
    }

    private LocationListener getLocationListener(@NonNull final GoogleMap googleMap) {
        return new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
                googleMap.animateCamera(cameraUpdate);
            }
        };
    }

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		MapDemoActivityPermissionsDispatcher
                .onRequestPermissionsResult(this, requestCode, grantResults);
	}

    @Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Unrecoverable GoogleApiClient connection failure: " +
                connectionResult.getErrorMessage());
	}

}
