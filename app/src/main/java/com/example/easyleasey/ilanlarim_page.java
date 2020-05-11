package com.example.easyleasey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.easyleasey.Fragments.Home_fragment;
import com.example.easyleasey.Fragments.My_Ads_fragment;
import com.example.easyleasey.RegisterFragments.RegisterBireyselFragment;
import com.example.easyleasey.models.NewCarIlan;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ilanlarim_page extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView imageView;
    private ImageView btn_close;
    private TextView arac_markasi;
    private TextView arac_modeli;
    private TextView fiyati;
    private TextView vites_turu;
    private TextView yakit_turu;
    private ImageView btn_delete;
    private GoogleMap mMap;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilanlarim_page);

        imageView = findViewById(R.id.resimm);
        arac_markasi = findViewById(R.id.arac_markasi);
        arac_modeli = findViewById(R.id.arac_modeli);
        fiyati = findViewById(R.id.fiyati);
        vites_turu = findViewById(R.id.vites_turu);
        yakit_turu = findViewById(R.id.yakit_turu);
        btn_close = findViewById(R.id.close_ilan);
        btn_delete = findViewById(R.id.deletee);

        NewCarIlan ilan = (NewCarIlan) getIntent().getSerializableExtra("IlanOgesi");

        SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        latLng = new LatLng(ilan.getEnlem(),ilan.getBoylam());


        Glide.with(this).load(ilan.getIlan_foto()).into(imageView);
        arac_modeli.setText(ilan.getArac_Model());
        arac_markasi.setText(ilan.getArac_Adi());
        fiyati.setText(String.valueOf(ilan.getFiyat()));
        vites_turu.setText(ilan.getVites_Turu());
        yakit_turu.setText(ilan.getYakit_Turu());
        final String ilan_id = ilan.getIlan_Id();

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ilanlarim_page.this, Market_page.class);
                intent.putExtra("back","my_ads");
                startActivity(intent);
                finish();

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("İlanlar").child(ilan_id).removeValue();
                startActivity(new Intent(ilanlarim_page.this,Market_page.class));
                finish();
            }
        });



    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Araç Konumu");
        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        mMap.addMarker(markerOptions);
    }

}
