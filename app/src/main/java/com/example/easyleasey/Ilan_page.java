package com.example.easyleasey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.easyleasey.Fragments.Home_fragment;
import com.example.easyleasey.models.NewCarIlan;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Ilan_page extends AppCompatActivity implements OnMapReadyCallback {
    private ImageView imageView;
    private ImageView btn_close;
    private TextView arac_markasi;
    private TextView arac_modeli;
    private TextView fiyati;
    private TextView vites_turu;
    private TextView yakit_turu;
    private ImageView btn_chat;
    private GoogleMap mMap;
    private LatLng latng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_page);

        imageView = findViewById(R.id.resimm2);
        NewCarIlan text = (NewCarIlan) getIntent().getSerializableExtra("IlanOgesi");

        Glide.with(this).load(text.getIlan_foto()).into(imageView);

        arac_markasi = findViewById(R.id.arac_markasi2);
        arac_modeli = findViewById(R.id.arac_modeli2);
        fiyati = findViewById(R.id.fiyati2);
        vites_turu = findViewById(R.id.vites_turu2);
        yakit_turu = findViewById(R.id.yakit_turu2);
        btn_chat = findViewById(R.id.gotochat);
        btn_close = findViewById(R.id.close_ilan2);

        NewCarIlan ilan2 = (NewCarIlan) getIntent().getSerializableExtra("IlanOgesi");

        SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFrag.getMapAsync(this);
        System.out.println(ilan2.getBoylam());
        latng = new LatLng(ilan2.getEnlem(), ilan2.getBoylam());


        Glide.with(this).load(ilan2.getIlan_foto()).into(imageView);
        arac_modeli.setText(ilan2.getArac_Model());
        arac_markasi.setText(ilan2.getArac_Adi());
        fiyati.setText(String.valueOf(ilan2.getFiyat()));
        vites_turu.setText(ilan2.getVites_Turu());
        yakit_turu.setText(ilan2.getYakit_Turu());
        final String ilan_id = ilan2.getIlan_Id();

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ilan_page.this, Market_page.class));
                finish();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latng);
        markerOptions.title("Araç Konumu");
        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latng,15));
        mMap.addMarker(markerOptions);
    }
}
