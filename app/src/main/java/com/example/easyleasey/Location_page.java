package com.example.easyleasey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class Location_page extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    Geocoder geocoder;
    String address,goback,baslangic,bitis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_page);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mMap.clear();
                LatLng userLatLng = new LatLng(location.getLatitude(),location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(userLatLng).title("Konumunuz:"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng,12));
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 50, locationListener);
        }

        mMap.setOnMapLongClickListener(this);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0) {
            if (requestCode == 1) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 50, locationListener);
                }
            }
        }
    }

    @Override
    public void onMapLongClick(final LatLng latLng) {
        final String final_lat = String.valueOf(latLng.latitude);
        final String final_long = String.valueOf(latLng.longitude);

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title("Seçilen Konum:"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {

            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                address = addresses.get(0).getSubAdminArea() + "/" + addresses.get(0).getAdminArea();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Seçtiğiniz Konum: " + address);
                builder.setMessage("Onaylıyor musunuz?");

                builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Intent resultintent = new Intent();
                        resultintent.putExtra("address", address);
                        resultintent.putExtra("latitude",final_lat );
                        resultintent.putExtra("longitude",final_long);
                        setResult(RESULT_OK,resultintent);
                        finish();
                    }
                });

                builder.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            LatLng userLatLng = new LatLng(location.getLatitude(),location.getLongitude());
                            mMap.clear();
                            mMap.addMarker(new MarkerOptions().position(userLatLng).title("Konumunuz:"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng,12));
                        }
                        Toast.makeText(getApplicationContext(),"Lütfen Konum Seçiniz",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
            System.out.println(addresses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
