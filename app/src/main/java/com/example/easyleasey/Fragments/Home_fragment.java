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

import com.example.easyleasey.Adapters.Ilan_adapter;
import com.example.easyleasey.Chat_page;
import com.example.easyleasey.Filter_page;
import com.example.easyleasey.Ilan_page;
import com.example.easyleasey.R;
import com.example.easyleasey.models.NewCarIlan;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Home_fragment extends Fragment implements Ilan_adapter.onIlanListener {

    ImageView filterhome;
    ImageView go_chat;
    private RecyclerView recyclerView;
    private Ilan_adapter ilan_adapter;
    private List<NewCarIlan> mIlan;
    private Long aliszamani,iadezamani;
    private double enlem,boylam;
    private NewCarIlan newCarIlan;



    public Home_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView=view.findViewById(R.id.recycle_view_home);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mIlan = new ArrayList<>();
        ilan_adapter = new Ilan_adapter(getContext(),mIlan,this);
        recyclerView.setAdapter(ilan_adapter);


        filterhome = view.findViewById(R.id.filter);
        filterhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Filter_page.class));
            }
        });
        try {
            aliszamani = getActivity().getIntent().getExtras().getLong("aliszamani");
            iadezamani = getActivity().getIntent().getExtras().getLong("iadezamani");
            enlem = getActivity().getIntent().getExtras().getDouble("Enlem");
            boylam = getActivity().getIntent().getExtras().getDouble("Boylam");
        }
        catch (Exception e){
            aliszamani = 0L;
            iadezamani = 9999999999999L;
            enlem = 0;
            boylam = 0;
        }


        ilanlarioku();

        return view;
    }

    private void ilanlarioku(){

        FirebaseDatabase.getInstance().getReference("İlanlar").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mIlan.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    newCarIlan = snapshot.getValue(NewCarIlan.class);

                    if ((newCarIlan.getKiralama_Bitis()>iadezamani) || (iadezamani==0)){
                        if ((newCarIlan.getKiralama_Başlangic()<aliszamani) || (aliszamani ==0)){
                            mIlan.add(newCarIlan);
                        }
                    }
                    else
                    {
                        mIlan.add(newCarIlan);
                    }

                }
                ilan_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onIlanClik(int position) {
        mIlan.get(position);
        Intent intent = new Intent(getContext(), Ilan_page.class);
        intent.putExtra("IlanOgesi",(Serializable) mIlan.get(position));
        startActivity(intent);
    }
}
