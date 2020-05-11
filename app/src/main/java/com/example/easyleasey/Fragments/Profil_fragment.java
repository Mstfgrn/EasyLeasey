package com.example.easyleasey.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.easyleasey.Phone_page;
import com.example.easyleasey.R;
import com.google.firebase.auth.FirebaseAuth;


public class Profil_fragment extends Fragment {

    Button btn_logout;

    public Profil_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        btn_logout = view.findViewById(R.id.logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), Phone_page.class);
                startActivity(intent);
            }
        });
        return view;

    }
}
