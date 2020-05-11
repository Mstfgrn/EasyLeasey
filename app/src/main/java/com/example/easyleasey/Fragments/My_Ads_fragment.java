package com.example.easyleasey.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.easyleasey.Adapters.Ilanlarim_adapter;
import com.example.easyleasey.R;
import com.example.easyleasey.ilanlarim_page;
import com.example.easyleasey.models.NewCarIlan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class My_Ads_fragment extends Fragment implements Ilanlarim_adapter.onIlanListener {

    ImageView delete;
    ImageView go_chat;
    private RecyclerView recyclerView2;
    private Ilanlarim_adapter ilanlarim_adapter;
    private List<NewCarIlan> mIlan2;
    private NewCarIlan newCarIlan2;


    public My_Ads_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_my_ads, container, false);

        recyclerView2=view.findViewById(R.id.recycle_view_myads);
        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView2.setLayoutManager(linearLayoutManager);

        mIlan2 = new ArrayList<>();
        ilanlarim_adapter = new Ilanlarim_adapter(getContext(),mIlan2,this);
        recyclerView2.setAdapter(ilanlarim_adapter);

        ilanlarioku();

        return view;
    }

    private void ilanlarioku(){
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseDatabase.getInstance().getReference("İlanlar").orderByChild("kullanıcı_Id").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mIlan2.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                     newCarIlan2= snapshot.getValue(NewCarIlan.class);

                    //filtre için uygun olabilir
                    mIlan2.add(newCarIlan2);
                }
                ilanlarim_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onIlanClik(int position) {
            mIlan2.get(position);
            Intent intent = new Intent(getContext(), ilanlarim_page.class);
            intent.putExtra("IlanOgesi",(Serializable) mIlan2.get(position));
            startActivity(intent);
    }


}
