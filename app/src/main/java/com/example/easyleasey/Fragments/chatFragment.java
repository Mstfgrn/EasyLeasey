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

import com.example.easyleasey.Adapters.Chat_adapter;
import com.example.easyleasey.Adapters.Ilan_adapter;
import com.example.easyleasey.Adapters.Ilanlarim_adapter;
import com.example.easyleasey.Adapters.MessageAdapter;
import com.example.easyleasey.MessageActivity;
import com.example.easyleasey.R;
import com.example.easyleasey.ilanlarim_page;
import com.example.easyleasey.models.Chat;
import com.example.easyleasey.models.NewCarIlan;
import com.example.easyleasey.models.UsersBireysel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class chatFragment extends Fragment implements Chat_adapter.onChatListener {

    private RecyclerView recyclerView3;
    private Chat_adapter chat_adapter;
    private List<UsersBireysel> usersBireysels;
    private List<String> kullanici_ids;



    public chatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView3=view.findViewById(R.id.recycle_view_chat);
        recyclerView3.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView3.setLayoutManager(linearLayoutManager);

        usersBireysels = new ArrayList<>();
        chat_adapter = new Chat_adapter(getContext(),usersBireysels,this);
        recyclerView3.setAdapter(chat_adapter);

        readUsers();
        return view;
    }

    @Override
    public void onChatClik(int position) {
        usersBireysels.get(position);
        Intent intent = new Intent(getContext(), MessageActivity.class);
        intent.putExtra("userid", usersBireysels.get(position).getId());
        startActivity(intent);


    }

    private void readUsers(){
        final String myid = FirebaseAuth.getInstance().getUid();
        kullanici_ids = new ArrayList<>();


        FirebaseDatabase.getInstance().getReference("Chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot :  dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid)){
                        kullanici_ids.add(chat.getSender());
                    }
                    if (chat.getSender().equals(myid)){
                        kullanici_ids.add(chat.getReceiver());
                    }
                }
                Set<String> set = new HashSet<>(kullanici_ids);
                kullanici_ids.clear();
                kullanici_ids.addAll(set);
                readkullanici(kullanici_ids);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readkullanici(List<String> kullanici_ids ){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Kullanicilar").child("Bireysel");

        usersBireysels.clear();
        for (String kul: kullanici_ids){
            System.out.println(kul);
            reference.child(kul).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    usersBireysels.add(dataSnapshot.getValue(UsersBireysel.class));
                    chat_adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

    }

}
