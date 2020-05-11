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
import com.example.easyleasey.models.UsersBireysel;

import java.util.List;

public class Chat_adapter extends RecyclerView.Adapter<Chat_adapter.ViewHolder> {

    public Context mContext;
    private List<UsersBireysel> usersBireysels;
    private onChatListener monChatListener;


    public Chat_adapter(Context mContext, List<UsersBireysel> usersBireysels, onChatListener monChatListener) {
        this.mContext = mContext;
        this.usersBireysels = usersBireysels;
        this.monChatListener = monChatListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.chatogesi,parent,false);
        System.out.println("2");
        return new Chat_adapter.ViewHolder(view,monChatListener);

    }

    @Override
    public void onBindViewHolder(@NonNull Chat_adapter.ViewHolder holder, int position) {

        System.out.println("1");
        UsersBireysel usersBireysel = usersBireysels.get(position);
        System.out.println(usersBireysel.getImageURL());
        Glide.with(mContext).load(usersBireysel.getImageURL()).into(holder.profile_image);
        holder.kullanici_adi.setText(String.valueOf(usersBireysel.getAdi()));
    }


    @Override
    public int getItemCount() {
        System.out.println(usersBireysels.size());
        return usersBireysels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView profile_image;
        public TextView kullanici_adi;
        onChatListener onChatListener;

        public ViewHolder(@NonNull View itemView, onChatListener onChatListener) {
            super(itemView);

            profile_image = itemView.findViewById(R.id.profile_image);
            kullanici_adi = itemView.findViewById(R.id.kullanici_adi);
            this.onChatListener = onChatListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onChatListener.onChatClik(getAdapterPosition());
        }
    }

    public interface onChatListener {
        void onChatClik(int position);
    }
}