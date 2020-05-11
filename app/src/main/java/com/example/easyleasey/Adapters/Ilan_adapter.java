package com.example.easyleasey.Adapters;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easyleasey.MessageActivity;
import com.example.easyleasey.models.NewCarIlan;
import com.example.easyleasey.R;


import java.util.List;
import java.util.Locale;

public class Ilan_adapter extends RecyclerView.Adapter<Ilan_adapter.ViewHolder> {

    public Context mContext;
    private List<NewCarIlan> mIlan;
    private onIlanListener monIlanListener;

    //private FirebaseUser mevcutfirebaseuser;

    public Ilan_adapter(Context mContext, List<NewCarIlan> mIlan,onIlanListener onIlanListener) {
        this.mContext = mContext;
        this.mIlan = mIlan;
        this.monIlanListener = onIlanListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.ilanogesi,parent,false);
        System.out.println("3");
        return new Ilan_adapter.ViewHolder(view,monIlanListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NewCarIlan newCarIlan = mIlan.get(position);

        Glide.with(mContext).load(newCarIlan.getIlan_foto()).into(holder.ilanfoto);

        holder.ilanfiyat.setText(String.valueOf(newCarIlan.getFiyat()));
        holder.aracmarka.setText(newCarIlan.getArac_Model());

        holder.ilankonum.setText(newCarIlan.getIlan_Konum());

    }

    @Override
    public int getItemCount() {
        return mIlan.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView ilanfoto;
        public ImageView gotochat;
        public TextView ilanfiyat;
        public TextView aracmarka;
        public TextView ilankonum;
        onIlanListener onIlanListener;

        public ViewHolder(@NonNull View itemView, onIlanListener onIlanListener) {
            super(itemView);

            ilanfoto = itemView.findViewById(R.id.ilan_foto);
            gotochat = itemView.findViewById(R.id.go_chat);
            ilanfiyat = itemView.findViewById(R.id.ilanfiyat);
            aracmarka = itemView.findViewById(R.id.arac_marka);
            ilankonum = itemView.findViewById(R.id.ilankonum);

            this.onIlanListener = onIlanListener;

            itemView.setOnClickListener(this);

            gotochat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MessageActivity.class);
                    intent.putExtra("userid",mIlan.get(getAdapterPosition()).getKullanıcı_Id());
                    mContext.startActivity(intent);
                }
            });

        }

        @Override
        public void onClick(View v) {
            onIlanListener.onIlanClik(getAdapterPosition());
        }

    }

    public interface onIlanListener{
        void onIlanClik(int position);
    }



}
