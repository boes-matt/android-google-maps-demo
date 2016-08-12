package com.example.mapdemo;

import android.Manifest;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.MarkerOptions;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MapDemoActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MapDemoActivity.class.getSimpleName();

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private SupportMapFragment mFragment;
    private GoogleApiClient.ConnectionCallbacks mLocationUpdatesConnectionCallbacks;
    private LocationListener mLocationUpdatesListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_demo_activity);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(LocationServices.API).build();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(60000);        // 60 seconds
        mLocationRequest.setFastestInterval(5000);  //  5 seconds

        FragmentManager fm = getSupportFragmentManager();
        mFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (mFragment != null) {
            setUpMap(mGoogleApiClient, mFragment);
        }
    }

    private void setUpMap(@NonNull final GoogleApiClient client,
                          @NonNull final SupportMapFragment fragment) {
        fragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                addMarker(googleMap, "Sydney", new LatLng(-34, 151));
                MapDemoActivityPermissionsDispatcher.mapIsReadyForCurrentLocationWithCheck(
                        MapDemoActivity.this, client, googleMap);
            }
        });
    }

    private void addMarker(@NonNull GoogleMap map, String title, LatLng latLng) {
        map.addMarker(new MarkerOptions().title(title).position(latLng));
    }

    @SuppressWarnings("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    protected void mapIsReadyForCurrentLocation(@NonNull final GoogleApiClient client,
                                                @NonNull final GoogleMap map) {
        map.setMyLocationEnabled(true);
        client.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                client.unregisterConnectionCallbacks(this);
                MapDemoActivityPermissionsDispatcher.clientIsConnectedForCurrentLocationWithCheck(
                        MapDemoActivity.this, client, map);
            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        });
    }

    @SuppressWarnings("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    protected void clientIsConnectedForCurrentLocation(@NonNull final GoogleApiClient client,
                                                       @NonNull final GoogleMap map) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(client);
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            map.animateCamera(cameraUpdate);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mFragment != null) {
            beginLocationUpdates(mGoogleApiClient, mFragment);
        }
    }

    private void beginLocationUpdates(@NonNull final GoogleApiClient client,
                                      @NonNull final SupportMapFragment fragment) {
        fragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mapIsReadyForLocationUpdates(client, googleMap);
            }
        });
    }

    private void mapIsReadyForLocationUpdates(@NonNull final GoogleApiClient client,
                                              @NonNull final GoogleMap map) {
        mLocationUpdatesListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "New location update: " + location);
                Toast.makeText(MapDemoActivity.this, location.toString(), Toast.LENGTH_LONG).show();
                doSomething(map);
            }
        };

        mLocationUpdatesConnectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                MapDemoActivityPermissionsDispatcher.clientIsConnectedForLocationUpdatesWithCheck(
                        MapDemoActivity.this, client, mLocationUpdatesListener);
            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        };
        client.registerConnectionCallbacks(mLocationUpdatesConnectionCallbacks);
    }

    private void doSomething(GoogleMap map) {
        // TODO
    }

    @SuppressWarnings("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    protected void clientIsConnectedForLocationUpdates(@NonNull final GoogleApiClient client,
                                                       @NonNull final LocationListener listener) {
        LocationServices.FusedLocationApi
                .requestLocationUpdates(client, mLocationRequest, listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLocationUpdatesListener != null) {
            if (mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi
                        .removeLocationUpdates(mGoogleApiClient, mLocationUpdatesListener);
            }
            mLocationUpdatesListener = null;
        }

        if (mLocationUpdatesConnectionCallbacks != null) {
            mGoogleApiClient.unregisterConnectionCallbacks(mLocationUpdatesConnectionCallbacks);
            mLocationUpdatesConnectionCallbacks = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MapDemoActivityPermissionsDispatcher
                .onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Unrecoverable GoogleApiClient connection failure: " +
                connectionResult.getErrorMessage());
    }

}
