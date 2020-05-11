package com.example.easyleasey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.easyleasey.Fragments.Home_fragment;
import com.example.easyleasey.Fragments.My_Ads_fragment;
import com.example.easyleasey.Fragments.Profil_fragment;
import com.example.easyleasey.Fragments.chatFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Market_page extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment = null;
    private ImageView filterhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketpage);


        bottomNavigationView = findViewById(R.id.bottom_navigation);

        try{
            if(getIntent().getExtras().getString("back").equals("my_ads")){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new My_Ads_fragment()).commit();
            }
            else{
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Home_fragment()).commit();
            }
        }catch (Exception e){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new Home_fragment()).commit();
        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        fragment = new Home_fragment();
                        break;

                    case R.id.nav_profil:
                        startActivity(new Intent(Market_page.this,Profile_page.class));
                        break;

                    case R.id.nav_add:
                        startActivity(new Intent(Market_page.this,Addcar_page.class));
                        break;

                    case R.id.nav_myads:
                        fragment = new My_Ads_fragment();
                        break;

                    case R.id.nav_chat:
                        fragment = new chatFragment();
                        break;
                }
                if (fragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).commit();
                }
                return true;
            }
        });
    }
}
