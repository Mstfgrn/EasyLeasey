package com.example.easyleasey;
// Created by MstfGrgn on 20.04.2020.
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.easyleasey.RegisterFragments.RegisterBireyselFragment;
import com.example.easyleasey.RegisterFragments.RegisterKurumsalFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Register_pages extends AppCompatActivity {
    Button btn_bireysel,btn_kurumsal;
    FirebaseUser mCurrentuser;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pages);
        btn_bireysel = (Button)(findViewById(R.id.btn_bireysel));
        btn_kurumsal = (Button)(findViewById(R.id.btn_kurumsal));

        FragmentManager fragmentManager = getSupportFragmentManager();
        RegisterBireyselFragment fragment = new RegisterBireyselFragment();
        btn_bireysel.setTextSize(18);
        btn_kurumsal.setTextSize(16);
        btn_bireysel.setTextColor(Color.parseColor("Black"));
        btn_kurumsal.setTextColor(Color.parseColor("#5f6769"));
        fragmentManager.beginTransaction().replace(R.id.fragment_conteiner,fragment).commit();
        btn_bireysel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                RegisterBireyselFragment fragment = new RegisterBireyselFragment();
                btn_bireysel.setTextSize(18);
                btn_kurumsal.setTextSize(16);
                btn_bireysel.setTextColor(Color.parseColor("Black"));
                btn_kurumsal.setTextColor(Color.parseColor("#5f6769"));
                fragmentManager.beginTransaction().replace(R.id.fragment_conteiner,fragment).commit();

            }
        });
        btn_kurumsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                RegisterKurumsalFragment fragment = new RegisterKurumsalFragment();
                btn_bireysel.setTextSize(16);
                btn_kurumsal.setTextSize(18);
                btn_bireysel.setTextColor(Color.parseColor("#5f6769"));
                btn_kurumsal.setTextColor(Color.parseColor("Black"));
                fragmentManager.beginTransaction().replace(R.id.fragment_conteiner, fragment).commit();


            }
        });
    }


}
