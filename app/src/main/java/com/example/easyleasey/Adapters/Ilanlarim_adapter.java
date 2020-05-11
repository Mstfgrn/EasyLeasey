package com.example.easyleasey.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easyleasey.R;
import com.example.easyleasey.models.NewCarIlan;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Ilanlarim_adapter extends RecyclerView.Adapter<Ilanlarim_adapter.ViewHolder> {

    public Context mContext;
    private List<NewCarIlan> mIlan2;
    private onIlanListener monIlanListener;

    public Ilanlarim_adapter(Context mContext, List<NewCarIlan> mIlan2, onIlanListener onIlanListener) {
        this.mContext = mContext;
        this.mIlan2 = mIlan2;
        this.monIlanListener = onIlanListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ilanlarim,parent,false);
        return new Ilanlarim_adapter.ViewHolder(view,monIlanListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final NewCarIlan newCarIlan = mIlan2.get(position);

        Glide.with(mContext).load(newCarIlan.getIlan_foto()).into(holder.ilanfoto);

        holder.ilanfiyat.setText(String.valueOf(newCarIlan.getFiyat()));
        holder.aracmarka.setText(newCarIlan.getArac_Model());
        holder.ilankonum.setText(newCarIlan.getIlan_Konum());

        holder.delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String deleted =mIlan2.get(position).getIlan_Id();
                FirebaseDatabase.getInstance().getReference("İlanlar").child(deleted).removeValue();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mIlan2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView ilanfoto;
        public ImageView delete_item;
        public TextView ilanfiyat;
        public TextView aracmarka;
        public TextView ilankonum;
        onIlanListener onIlanListener;

        public ViewHolder(@NonNull final View itemView, onIlanListener onIlanListener) {
            super(itemView);

            ilanfoto = itemView.findViewById(R.id.ilan_foto);
            delete_item = itemView.findViewById(R.id.delete);
            ilanfiyat = itemView.findViewById(R.id.ilanfiyat);
            aracmarka = itemView.findViewById(R.id.arac_marka);
            ilankonum = itemView.findViewById(R.id.ilankonum);

            this.onIlanListener = onIlanListener;
            itemView.setOnClickListener(this);
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
